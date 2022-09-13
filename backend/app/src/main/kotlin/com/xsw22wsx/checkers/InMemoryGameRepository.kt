package com.xsw22wsx.checkers

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryGameRepository : GameRepository {
    private val games = mutableMapOf<UUID, OnlineCheckersGame>()

    override fun findById(id: UUID): OnlineCheckersGame? = games[id]

    override fun save(game: OnlineCheckersGame): OnlineCheckersGame {
        games[game.id] = game
        return game
    }

    override suspend fun findByPlayerToken(token: String): OnlineCheckersGame? = games
        .values
        .firstOrNull { it.playerSlots.any { slot -> slot.token == token } }
}