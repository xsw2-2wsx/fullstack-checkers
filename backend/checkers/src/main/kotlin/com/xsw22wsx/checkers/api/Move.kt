package com.xsw22wsx.checkers.api

class Move(val from: Position, val to: Position)

infix fun Position.to(to: Position): Move = Move(this, to)