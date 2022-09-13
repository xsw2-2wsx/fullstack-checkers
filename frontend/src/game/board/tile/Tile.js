import React from "react";
import styles from './Tile.module.scss';
import whitePiece from 'assets/white.svg';
import whiteKing from 'assets/white_king.svg';
import blackPiece from 'assets/black.svg';
import blackKing from 'assets/black_king.svg';

export const Tile = ({rowNum, colNum, piece, selected, clickHandler}) => {

    let className = styles['tile-white']
    if(colNum % 2 == 0 && rowNum % 2 == 1) className = styles['tile-black'];
    if(colNum % 2 == 1 && rowNum % 2 == 0) className = styles['tile-black'];

    if(selected) className += ` ${styles.selected}`;

    let image;
    if(piece) {
        if(piece.owner === 'WHITE' && piece.type === 'Piece') image = whitePiece
        else if(piece.owner === 'WHITE' && piece.type === 'King') image = whiteKing
        else if(piece.owner === 'BLACK' && piece.type === 'Piece') image = blackPiece
        else if(piece.owner === 'BLACK' && piece.type === 'King') image = blackKing
    }
    

    return (
        <div className={className} onClick={() => clickHandler(piece, {rowNum, colNum})}>
            {piece && <img src={image}></img>}
            <div className={styles.overlay}></div>
        </div>
    )
}




