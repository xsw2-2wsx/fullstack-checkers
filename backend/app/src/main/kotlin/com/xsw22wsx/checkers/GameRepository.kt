package com.xsw22wsx.checkers

import java.util.UUID

interface GameRepository {
    fun findById(id: UUID): OnlineCheckersGame?
    fun save(game: OnlineCheckersGame): OnlineCheckersGame
    suspend fun findByPlayerToken(token: String): OnlineCheckersGame?
}