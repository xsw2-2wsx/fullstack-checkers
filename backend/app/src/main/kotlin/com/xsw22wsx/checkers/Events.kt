package com.xsw22wsx.checkers

import com.xsw22wsx.checkers.api.Player
import com.xsw22wsx.checkers.api.Update

sealed class Event(val type: String)

data class GameStatusChangeEvent(
    val newStatus: OnlineCheckersGame.Status
) : Event("game_status_changed")

data class PlayerJoinedEvent(
    val color: Player,
) : Event("player_joined")

data class BoardUpdateEvent(
    val update: Update,
) : Event("board_update")

data class GameEndEvent(
    val winner: Player,
) : Event("game_end")

data class TurnChangeEvent(
    val player: Player
) : Event("turn_change")
