package com.xsw22wsx.checkers.io.rest

import com.xsw22wsx.checkers.GameService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/game")
class GameController(
    private val gameService: GameService,
) {

    @PostMapping
    fun createGame(): String = gameService.createGame().toString()

    @PatchMapping("/{id}/join")
    suspend fun joinGame(@PathVariable("id") gameId: UUID): String = gameService.joinGame(gameId)


    @PostMapping("{id}/move")
    suspend fun makeMove(
        @PathVariable("id") gameId: UUID,
        @RequestHeader("Authorization", required = true) token: String,
        @RequestBody moveModel: MoveModel,
    ) = gameService.move(gameId, token, moveModel.toMove())

    @GetMapping("{id}/state")
    suspend fun getGameState(
        @PathVariable("id") gameId: UUID,
        @RequestHeader("Authorization", required = true) token: String,
    ): GameModel {
        val game = gameService.findGame(gameId, token)
        val playingAs = game.playerSlots.find { it.token == token }!!.player

        return GameModel(
            game.id,
            game.status,
            game.lastStatusChange,
            game.game.currentPlayer,
            game.game.winner,
            game.game.board.currentState.map { column -> column.map {
                if(it != null) GameModel.PieceModel(it.type, it.owner.toString()) else null
            } },
            playingAs
        )
    }


}