package it.mrschyzo.configuration

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithDefault
import io.smallrye.config.WithName

@ConfigMapping(prefix = OBJECT_POOL_MESSAGEDIGEST)
interface ObjectPoolConfiguration {
    @WithName(OBJECT_POOL_MESSAGEDIGEST_SIZE_KEY)
    @WithDefault(OBJECT_POOL_MESSAGEDIGEST_SIZE_DEFAULT)
    fun size(): Int
}
