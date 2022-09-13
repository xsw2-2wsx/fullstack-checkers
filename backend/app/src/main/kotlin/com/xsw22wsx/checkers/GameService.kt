package com.xsw22wsx.checkers

import com.xsw22wsx.checkers.api.Move
import com.xsw22wsx.checkers.exceptions.AllSlotsClaimedException
import com.xsw22wsx.checkers.exceptions.GameAccessDeniedException
import com.xsw22wsx.checkers.exceptions.GameNotFoundException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.withLock
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.coroutines.CoroutineContext

@Service
class GameService(
    private val gameRepository: GameRepository,
): CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    fun createGame(): UUID {
        val game = OnlineCheckersGame()
        gameRepository.save(game)

        return game.id
    }

    suspend fun findGame(gameId: UUID, playerToken: String): OnlineCheckersGame {
        val game = gameRepository.findById(gameId)?: throw GameNotFoundException()
        if(game.playerSlots.none { it.token == playerToken }) throw GameAccessDeniedException()

        return game
    }


    suspend fun joinGame(gameId: UUID): String {
        val game = gameRepository.findById(gameId)?: throw GameNotFoundException()

        game.lock.withLock {
            val slot = game.playerSlots.filter { !it.isClaimed }.randomOrNull()?: throw AllSlotsClaimedException()
            slot.isClaimed = true
            game.eventStream.emit(PlayerJoinedEvent(slot.player))

            if(game.allPlayerJoined) startGame(game)

            return slot.token
        }
    }

    private fun startGame(game: OnlineCheckersGame) = launch {
        game.status = OnlineCheckersGame.Status.RUNNING
        game.eventStream.emit(GameStatusChangeEvent(OnlineCheckersGame.Status.RUNNING))
        game.game.updates.collect { event ->
            when(event) {
                is com.xsw22wsx.checkers.api.BoardUpdateEvent -> game.eventStream.emit(BoardUpdateEvent(event.update))
                is com.xsw22wsx.checkers.api.TurnChangeEvent -> game.eventStream.emit(TurnChangeEvent(event.player))
            }

        }
    }

    suspend fun move(gameId: UUID, playerToken: String, move: Move) {
        val game = gameRepository.findById(gameId)?: throw GameNotFoundException()
        val playerSlot = game.playerSlots.firstOrNull { it.token == playerToken }?: throw GameAccessDeniedException()

        game.makeMove(move, playerSlot)
    }

    suspend fun getEventStream(playerToken: String): Flow<Event> = gameRepository
        .findByPlayerToken(playerToken)
        ?.eventStream
        ?: throw GameNotFoundException()

}