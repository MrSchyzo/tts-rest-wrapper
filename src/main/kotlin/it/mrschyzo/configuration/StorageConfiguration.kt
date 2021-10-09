package it.mrschyzo.configuration

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithConverter
import io.smallrye.config.WithDefault
import it.mrschyzo.utils.types.PosLong
import java.nio.file.Path

@ConfigMapping(prefix = "storage.filesystem")
interface StorageConfiguration {
    @WithConverter(StringToPathConverter::class)
    @WithDefault(".")
    fun root(): Path

    @WithConverter(PositiveLongCoercionConverter::class)
    @WithDefault("3")
    fun depth(): PosLong
}
