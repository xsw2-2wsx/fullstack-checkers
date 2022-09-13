import React, { useState, useEffect } from "react";
import { useLocalStorage } from "../utils/Hooks";
import ApiClient from '../ApiClient';

export default function useGame() {
    const { gameId, token } = useGameCredentials()
    const [gameState, setGameState] = useState()
    const eventCallbacks = {}

    useEffect(() => {
        ApiClient.fetchGameState(gameId, token).then(state => setGameState(state));
    }, [gameId, token]);

    useEffect(() => {
        const connectWs = async () => {
            const ws = await ApiClient.getEventsWebSocket(token);
            ws.onmessage = (msg) =>  { 
                const event = JSON.parse(msg.data);
                adjustGameStateForEvent(event);
                if(eventCallbacks[event.type]) eventCallbacks[event.type]()
            }

            return () => ws.close();
        }

        connectWs().catch(console.error)
    }, [gameId, token]);

    const adjustGameStateForEvent = (event) => {
        if(event.type === 'game_status_changed') {
            setGameState(prev => { prev.status = event.newStatus; return { ... prev} });
            return;
        }

        if(event.type == 'board_update') {
            setGameState(prev => { 
                prev.board[event.update.position.x][event.update.position.y] = event.update.piece;
                return {...prev };
            });
            return;
        }

        if(event.type == 'turn_change') {
            setGameState(prev => { 
                prev.currentPlayer = event.player;
                return { ...prev };
            });
        }
    }

    const makeMove = (from, to) => {
        if(gameState.status == 'FINISHED') return;
        ApiClient.makeMove(gameId, token, from, to).catch(console.error);
    }

    return { gameState, makeMove, eventCallbacks };
}


function useGameCredentials() {
    const [gameId, setGameId] = useLocalStorage('gameId');
    const [token, setToken] = useLocalStorage('token');

    return { gameId, token }
}