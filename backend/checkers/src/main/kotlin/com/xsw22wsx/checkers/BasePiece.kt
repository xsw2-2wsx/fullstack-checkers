package com.xsw22wsx.checkers

import com.xsw22wsx.checkers.api.Piece
import com.xsw22wsx.checkers.api.Position

interface BasePiece : Piece {

    var currentPosition: Position

    val canJump: Boolean

    val hasLegalMove: Boolean
}