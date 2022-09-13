import React from "react";
import styles from "./Sidebar.module.scss";

export const Sidebar = ({ gameState }) => {
    return (
        <div className={styles.main}>
            <h2>Checkers</h2>

            <p>Playing as: {gameState.playingAs}</p>
            <p>Game status: {gameState.status}</p>
            <p>Now turn of: {gameState.currentPlayer}</p>
            {gameState.winner && <p>Winner: {gameState.winner}</p>}

            <p>GameId: {gameState.id}</p>
        </div>
    )
}


export default Sidebar;