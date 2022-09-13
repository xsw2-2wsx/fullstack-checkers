package com.xsw22wsx.checkers.api

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Position(
    val x: Int,
    val y: Int,
) {

    init {
        if(x !in 0..7 || y !in 0..7)
            throw PositionOutOfRangeException(x, y)
    }
}

fun p(x: Int, y: Int) = Position(x, y)
fun from(x: Int, y: Int) = Position(x, y)
fun to(x: Int, y: Int) = Position(x, y)


infix fun Position.distanceTo(other: Position): Double {
    return sqrt((x - other.x).toDouble().pow(2) + (y - other.y).toDouble().pow(2))
}

val ONE_TILE_DIAGONAL_DISTANCE: Double = sqrt(2.0)

val Int.diagonalTiles
    get() = this * ONE_TILE_DIAGONAL_DISTANCE


data class Vector(
    val x: Int,
    val y: Int,
)

fun v(x: Int, y: Int) = Vector(x, y)

fun Vector.scale(factor: Double): Vector {
    return Vector((x * factor).toInt(), (y * factor).toInt())
}

fun vectorBetween(from: Position, to: Position): Vector {
    return Vector(to.x - from.x, to.y - from.y)
}

fun diagonalVectorsFor(player: Player): Set<Vector> {
    val yOffset = when(player) {
        Player.WHITE -> 1
        Player.BLACK -> -1
    }

    return setOf(v(1, yOffset), v(-1, yOffset))
}

val diagonalVectors = setOf(v(1, 1), v(-1, 1), v(1, -1), v(-1, -1))

fun Position.translate(vector: Vector): Position {
    return Position(x + vector.x, y + vector.y)
}


fun Position.tryTranslate(vector: Vector): Position? = try {
    Position(x + vector.x, y + vector.y)
} catch (c: PositionOutOfRangeException) {
    null
}