package com.xsw22wsx.checkers.exceptions

open class OnlineCheckersException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}