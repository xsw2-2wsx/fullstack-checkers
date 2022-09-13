package com.xsw22wsx.checkers

import com.xsw22wsx.checkers.api.*

class KingPiece(
    override val owner: Player,
    private val standardBoard: StandardBoard,
    override var currentPosition: Position,
) : BasePiece {
    override val type: String = "King"

    override suspend fun moveTo(position: Position) {
        if (currentPosition.x - position.x == 0 || currentPosition.y - position.y == 0)
            throw CheckersException("$type can only move diagonally")

        if (standardBoard.pieceAt(position) != null)
            throw CheckersException("$type cannot move to a position with another piece")

        when {
            currentPosition distanceTo position == 1.diagonalTiles-> move(position)
            currentPosition distanceTo position == 2.diagonalTiles -> jump(position)
            else -> throw CheckersException("You can only move 1 tile diagonally or jump")
        }

    }

    private suspend fun move(position: Position) {
        if (standardBoard.pieces.filter { it.owner == owner }.any { canJump })
            throw CheckersException("You must make a jump if one is available")

        standardBoard.update(currentPosition, null)
        standardBoard.update(position, this)
    }

    private suspend fun jump(position: Position) {
        val jumpedPosition = currentPosition.translate(vectorBetween(currentPosition, position).scale(0.5))
        val jumpedPiece = standardBoard.pieceAt(jumpedPosition) ?: throw CheckersException("No piece to jump over")

        if (jumpedPiece.owner == owner) throw CheckersException("$type cannot jump over own piece")

        standardBoard.update(position, this)
        standardBoard.update(jumpedPosition, null)
    }

    override val canJump: Boolean
        get() {
            for (v in diagonalVectors.map { it.scale(2.0) }) {

                if (standardBoard.pieceAt(currentPosition.tryTranslate(v)?: continue) != null) continue
                val jumpedPiece = standardBoard.pieceAt(currentPosition.translate(v.scale(0.5))) ?: continue
                if (jumpedPiece.owner != owner) return true
            }
            return false
        }

    private val canMove: Boolean
        get() {
            for (v in diagonalVectors)
                if (standardBoard.pieceAt(currentPosition.tryTranslate(v)?: continue) == null) return true

            return false
        }

    override val hasLegalMove: Boolean
        get() = canMove || canJump
}