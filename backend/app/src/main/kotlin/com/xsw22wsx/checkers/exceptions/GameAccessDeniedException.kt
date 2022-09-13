package com.xsw22wsx.checkers.exceptions

class GameAccessDeniedException(message: String = "You are not authorized to perform this action")
    : OnlineCheckersException(message)