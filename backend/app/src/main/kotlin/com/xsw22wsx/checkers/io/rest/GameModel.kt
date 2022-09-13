package com.xsw22wsx.checkers.io.rest

import com.xsw22wsx.checkers.OnlineCheckersGame
import com.xsw22wsx.checkers.api.Piece
import com.xsw22wsx.checkers.api.Player
import java.time.LocalDateTime
import java.util.UUID

data class GameModel(
    val id: UUID,
    val status: OnlineCheckersGame.Status,
    val lastStatusChange: LocalDateTime,
    val currentPlayer: Player,
    val winner: Player?,
    val board: List<List<PieceModel?>>,
    val playingAs: Player
) {


    data class PieceModel(
        val type: String,
        val owner: String,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameModel

        if (id != other.id) return false
        if (status != other.status) return false
        if (lastStatusChange != other.lastStatusChange) return false
        if (currentPlayer != other.currentPlayer) return false
        if (winner != other.winner) return false
        if (board != other.board) return false
        if (playingAs != other.playingAs) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + lastStatusChange.hashCode()
        result = 31 * result + currentPlayer.hashCode()
        result = 31 * result + (winner?.hashCode() ?: 0)
        result = 31 * result + board.hashCode()
        result = 31 * result + playingAs.hashCode()
        return result
    }
}