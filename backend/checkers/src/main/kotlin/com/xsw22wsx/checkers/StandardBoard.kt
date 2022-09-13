package com.xsw22wsx.checkers

import com.xsw22wsx.checkers.api.*
import kotlinx.coroutines.flow.MutableSharedFlow

class StandardBoard(private val eventFlow: MutableSharedFlow<Event>) : Board {

    private val board = Array(8) { arrayOfNulls<BasePiece>(8) }
    init {
        initRow(0, Player.WHITE, true)
        initRow(1, Player.WHITE, false)
        initRow(2, Player.WHITE, true)

        initRow(7, Player.BLACK, false)
        initRow(6, Player.BLACK, true)
        initRow(5, Player.BLACK, false)
    }

    private fun initRow(row: Int, owner: Player, startWithGap: Boolean) {
        val condition = if(startWithGap) 1 else 0

        for(i in 0 until 8)
            if(i % 2 == condition)
                board[row][i] = StandardPiece(owner, this, p(row, i))
    }

    internal val pieces
        get() = board.flatMap { it.asIterable() }.filterNotNull()

    suspend fun update(position: Position, piece: BasePiece?): Piece? {
        val oldPiece = board[position.x][position.y]
        board[position.x][position.y] = piece
        piece?.currentPosition = position
        eventFlow.emit(BoardUpdateEvent(Update(position, piece)))
        return oldPiece
    }

    override fun pieceAt(position: Position): Piece? {
        return board[position.x][position.y]
    }

    override val currentState: Array<Array<Piece?>>
        get() {
            val clonedBoard = Array(8) { arrayOfNulls<Piece>(8) }
            for (i in 0 until 8) {
                for (j in 0 until 8) {
                    clonedBoard[i][j] = board[i][j]
                }
            }

            return clonedBoard
        }
}

