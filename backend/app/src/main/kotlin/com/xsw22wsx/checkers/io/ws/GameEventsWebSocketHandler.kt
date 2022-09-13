package com.xsw22wsx.checkers.io.ws

import com.fasterxml.jackson.databind.ObjectMapper
import com.xsw22wsx.checkers.AuthTicketService
import com.xsw22wsx.checkers.GameService
import com.xsw22wsx.checkers.exceptions.GameAccessDeniedException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class GameEventsWebSocketHandler(
    private val gameService: GameService,
    private val objectMapper: ObjectMapper,
    private val authTicketService: AuthTicketService,
) : WebSocketHandler {
    override fun handle(session: WebSocketSession): Mono<Void> = mono {
        val authTicket = UriComponentsBuilder.fromUri(session.handshakeInfo.uri).build()
            .queryParams["auth_ticket"]
            ?.firstOrNull()
            ?: throw GameAccessDeniedException("No authentication token present in ws handshake request")

        val authToken = authTicketService.validateTicket(authTicket)
            ?: throw GameAccessDeniedException("Invalid auth ticket")


        val eventStream = gameService.getEventStream(authToken)

        val messagePublisher = eventStream
            .map { session.textMessage(objectMapper.writeValueAsString(it)) }
            .asFlux()


        Mono.zip(session.send(messagePublisher), session.receive().then()).then().awaitSingleOrNull()
    }
}