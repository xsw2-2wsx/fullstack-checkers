import Modal from "react-bootstrap/Modal";
import { Button } from "react-bootstrap";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Board from "./board/Board";
import styles from "./Game.module.scss";
import Sidebar from "./sidebar/Sidebar";
import  useGame  from "./useGame";

export const Game = () => {
    const navigate = useNavigate();
    const { gameState, makeMove, eventCallbacks } = useGame();
    const [message, setMessage] = useState(null);

    const showMessage = (newMessage) => setMessage(newMessage);
    

    if(gameState) return (<> 
            <div className={styles.main}>
                <div className={styles['board-container']}>
                    <Board gameState={gameState} makeMove={makeMove} showMessage={showMessage} />
                </div>
                <div className={styles['sidebar-container']}>
                    <Sidebar gameState={gameState} />
                </div>
                
            </div>

            
            <Modal show={message} centered>
                <Modal.Header>
                    <Modal.Title>{message?.title}</Modal.Title>
                </Modal.Header>
            <Modal.Body>{message?.text}</Modal.Body>
            <Modal.Footer>
            <Button variant="primary" onClick={() => setMessage(null)}>{message?.buttonText ?? 'Got it'}</Button>
            </Modal.Footer>
            </Modal>

            
        </> 
    )
}

export default Game;
