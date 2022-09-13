package com.xsw22wsx.checkers.api

open class CheckersException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}