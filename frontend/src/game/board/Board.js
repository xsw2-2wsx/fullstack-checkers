import React, { useState } from "react";
import styles from "./Board.module.scss"
import { Tile } from "./tile/Tile";

export const Board = ({ gameState, makeMove, showMessage }) => {
    const boardTiles = [];
    const [selected, setSelected] = useState();

    const clickHandler = (piece, position) => {
        if(!selected) {
            if(gameState.currentPlayer != gameState.playingAs) { showMessage({ title: 'Invalid move', text: 'Its not your turn' }); return; }
            if(piece && piece.owner != gameState.playingAs) { showMessage({ title: 'Invalid move', text: 'You can only move you own pieces' }); return; }
            if(!piece) { showMessage({ title: 'No piece selected', text: 'Select a piece to move fist' }); return;}
        }
        

        if(!selected) {
            setSelected(position);
            return;
        }

        const from = selected;
        setSelected(null);

        makeMove(from, position);
    }


    gameState.board.forEach((row, rowNum) => {
        row.forEach((piece, colNum) => {
            const isSelected = selected && selected.colNum === colNum && selected.rowNum === rowNum;
            boardTiles.push(<Tile key={colNum * 10 + rowNum} rowNum={rowNum} colNum={colNum} piece={piece} selected={isSelected} clickHandler={clickHandler} />)
        })
    })


    return (
        <div className={styles.board} style={gameState.playingAs === 'WHITE' ? { transform: 'rotate(180deg)' } : {}}>
            {boardTiles}
        </div>
    )
}

export default Board;