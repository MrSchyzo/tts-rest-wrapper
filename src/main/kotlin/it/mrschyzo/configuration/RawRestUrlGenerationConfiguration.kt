package it.mrschyzo.configuration

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithDefault
import io.smallrye.config.WithName

@ConfigMapping(prefix = REST_GENERATION)
interface RawRestUrlGenerationConfiguration {
    @WithDefault(REST_GENERATION_ROUTE_DEFAULT)
    @WithName(REST_GENERATION_ROUTE_KEY)
    fun route(): String
}
