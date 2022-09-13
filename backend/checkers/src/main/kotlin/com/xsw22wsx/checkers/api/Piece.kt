package com.xsw22wsx.checkers.api

interface Piece {
    val owner: Player

    suspend fun moveTo(position: Position)

    val type: String

}