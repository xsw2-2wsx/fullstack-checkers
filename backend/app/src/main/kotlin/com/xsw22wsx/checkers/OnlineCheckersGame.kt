package com.xsw22wsx.checkers

import com.xsw22wsx.checkers.api.Game
import com.xsw22wsx.checkers.api.Move
import com.xsw22wsx.checkers.api.Player
import com.xsw22wsx.checkers.exceptions.OnlineCheckersException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.sync.Mutex
import java.time.LocalDateTime
import java.util.UUID

class OnlineCheckersGame(
    val id: UUID = UUID.randomUUID(),
    val playerSlots: List<OnlineCheckersPlayerSlot> = listOf(
        OnlineCheckersPlayerSlot(Player.WHITE),
        OnlineCheckersPlayerSlot(Player.BLACK),
    ),
) {
    enum class Status {
        WAITING_FOR_PLAYERS,
        RUNNING,
        FINISHED;
    }


    var lastStatusChange: LocalDateTime = LocalDateTime.now()
    var status: Status = Status.WAITING_FOR_PLAYERS
        set(value) {
            field = value
            lastStatusChange = LocalDateTime.now()
        }

    val game: Game = StandardGame()
    val eventStream: MutableSharedFlow<Event> = MutableSharedFlow()

    val lock: Mutex = Mutex()

    val allPlayerJoined: Boolean
        get() = playerSlots.all { it.isClaimed }

    suspend fun makeMove(move: Move, playerSlot: OnlineCheckersPlayerSlot) {
        if(status != Status.RUNNING) throw OnlineCheckersException("Game is not running")

        game.makeMove(move, playerSlot.player)

        if(game.isFinished) {
            eventStream.emit(GameEndEvent(game.winner!!))
            status = Status.FINISHED
            eventStream.emit(GameStatusChangeEvent(Status.FINISHED))
        }
    }

}

data class OnlineCheckersPlayerSlot(
    val player: Player,
    val token: String = generateToken(),
    var isClaimed: Boolean = false,
)