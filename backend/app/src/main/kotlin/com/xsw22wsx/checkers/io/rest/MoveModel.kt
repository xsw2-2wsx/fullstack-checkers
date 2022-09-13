package com.xsw22wsx.checkers.io.rest

import com.xsw22wsx.checkers.api.Move
import com.xsw22wsx.checkers.api.Position

data class MoveModel(
    val from: PositionModel,
    val to: PositionModel,
)

fun MoveModel.toMove() = Move(
    Position(from.x, from.y),
    Position(to.x, to.y)
)