package com.xsw22wsx.checkers

import org.springframework.stereotype.Service

@Service
class AuthTicketService {

    private val tickets: MutableMap<String, String> = HashMap()

    fun createTicket(token: String): String {
        val ticket = generateToken()
        tickets[ticket] = token
        return ticket
    }

    fun validateTicket(ticket: String): String? = tickets.remove(ticket)

}