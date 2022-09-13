package com.xsw22wsx.checkers.io.rest

import com.xsw22wsx.checkers.AuthTicketService
import com.xsw22wsx.checkers.exceptions.GameAccessDeniedException
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthTicketController(
    private val authTicketService: AuthTicketService
) {

    @RequestMapping("/game/ticket")
    fun getTicket(@RequestHeader("Authorization", required = true) token: String): String {
        if(token.isBlank()) throw GameAccessDeniedException()

        return authTicketService.createTicket(token)
    }

}