package com.xsw22wsx.checkers.api

import kotlinx.coroutines.flow.Flow

interface Game {

    suspend fun makeMove(move: Move, player: Player)

    val currentPlayer: Player

    val winner: Player?

    val isFinished: Boolean
        get() = winner != null

    val board: Board

    val updates: Flow<Event>
}