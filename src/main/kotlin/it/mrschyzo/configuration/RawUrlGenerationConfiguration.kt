package it.mrschyzo.configuration

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithDefault
import io.smallrye.config.WithName

@ConfigMapping(prefix = FILE_GENERATION)
interface RawUrlGenerationConfiguration {
    @WithDefault(FILE_GENERATION_SERVE_ROOT_DEFAULT)
    @WithName(FILE_GENERATION_SERVE_ROOT_KEY)
    fun serveRoot(): String

    @WithDefault(FILE_GENERATION_URL_SUFFIX_DEFAULT)
    @WithName(FILE_GENERATION_URL_SUFFIX_KEY)
    fun urlSuffix(): String
}
