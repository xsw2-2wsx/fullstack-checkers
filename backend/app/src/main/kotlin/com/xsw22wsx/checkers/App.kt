package com.xsw22wsx.checkers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
class App {

}

fun main(args: Array<String>) {
    runApplication<App>()
}
