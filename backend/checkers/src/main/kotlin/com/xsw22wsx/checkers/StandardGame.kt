package com.xsw22wsx.checkers

import com.xsw22wsx.checkers.api.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class StandardGame : Game {
    private val _updates: MutableSharedFlow<Event> = MutableSharedFlow()
    override val updates: Flow<Event> get() = _updates

    override val board = StandardBoard(_updates)

    override var currentPlayer: Player = Player.WHITE
        private set

    override var winner: Player? = null
        private set

    override suspend fun makeMove(move: Move, player: Player) {
        if(isFinished) throw CheckersException("Game is finished")
        if(player != currentPlayer) throw CheckersException("It's not your turn")

        val piece = board.pieceAt(move.from)?: throw CheckersException("No piece at ${move.from}")
        if(piece.owner != player) throw CheckersException("You can only move your own piece")

        piece.moveTo(move.to)

        currentPlayer = currentPlayer.opponent

        if(!hasLegalMove(currentPlayer))
            winner = currentPlayer.opponent

        _updates.emit(TurnChangeEvent(player.opponent))

    }

    private fun hasLegalMove(player: Player): Boolean = board
        .pieces
        .filter { it.owner == player }
        .any { it.hasLegalMove }
}