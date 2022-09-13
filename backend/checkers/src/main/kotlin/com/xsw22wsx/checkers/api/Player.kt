package com.xsw22wsx.checkers.api

interface PlayerType {
    val homeRow: Int
}

enum class Player : PlayerType {
    WHITE {
        override val homeRow: Int = 0
    },

    BLACK {
        override val homeRow: Int = 7
    };

}

val Player.opponent: Player
    get() = when (this) {
        Player.WHITE -> Player.BLACK
        Player.BLACK -> Player.WHITE
    }
