package com.xsw22wsx.checkers.api

import kotlinx.coroutines.flow.Flow

interface Board {
    fun pieceAt(position: Position): Piece?

    val currentState: Array<Array<Piece?>>
}