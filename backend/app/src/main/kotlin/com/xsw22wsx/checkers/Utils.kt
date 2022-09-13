package com.xsw22wsx.checkers

import java.security.SecureRandom
import java.util.Base64

val secureRandom = SecureRandom()

fun generateToken(): String {
    val bytes = ByteArray(180)
    secureRandom.nextBytes(bytes)

    return Base64.getEncoder().encodeToString(bytes)
}