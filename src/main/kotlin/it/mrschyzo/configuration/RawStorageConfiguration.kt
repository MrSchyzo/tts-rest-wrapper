package it.mrschyzo.configuration

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithDefault

/***
 * This interface exists only to work around this issue: https://github.com/MrSchyzo/tts-rest-wrapper/issues/1#issue-1021557908
 */
@ConfigMapping(prefix = "storage.filesystem")
interface RawStorageConfiguration {
    @WithDefault(".")
    fun root(): String

    @WithDefault("1")
    fun depth(): Long
}
