package com.xsw22wsx.checkers.api

class PositionOutOfRangeException(val x: Int, val y: Int) : CheckersException("Position x: $x, y: $y is out of range")