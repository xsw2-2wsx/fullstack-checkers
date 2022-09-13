import React from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import ApiClient from "./ApiClient";

export const JoinGame = () => {
    const params = useParams();
    const navigate = useNavigate()

    async function joinGame() {
        const gameId = params.gameId;

        const token = await ApiClient.joinGame(gameId);

        if(!token) throw Error("Failed to fetch the token");

        localStorage.setItem("gameId", gameId);
        localStorage.setItem("token", token);

        navigate(`/play`);
    }


    return (
        <>
            <h2>You have been invited to a checkers game</h2>
            <p>Game id: {params.gameId}. Use button below to join</p>
            <button onClick={joinGame} className="btn btn-primary">Join game</button>
        </>
    )
}