package com.xsw22wsx.checkers.api

sealed interface Event

class BoardUpdateEvent(val update: Update) : Event

class TurnChangeEvent(val player: Player) : Event