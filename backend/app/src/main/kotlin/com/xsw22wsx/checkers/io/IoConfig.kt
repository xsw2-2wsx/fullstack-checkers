package com.xsw22wsx.checkers.io

import com.xsw22wsx.checkers.io.ws.GameEventsWebSocketHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping

@Configuration
class IoConfig(
    @Value("\${app.cors-allowed-domain}") private val corsAllowedDomain: String
) {

    @Bean
    fun corsConfig() = object : WebFluxConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns("http://localhost:[*]", "https://$corsAllowedDomain:[*]")
                .allowedHeaders("*")
                .allowCredentials(true)
        }
    }

    @Bean
    fun eventStreamWsEndpointMapping(handler: GameEventsWebSocketHandler) = SimpleUrlHandlerMapping(mapOf(
        "/game/events" to handler
    ), Ordered.HIGHEST_PRECEDENCE)


}