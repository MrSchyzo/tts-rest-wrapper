package it.mrschyzo.configuration

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithDefault

/***
 * This interface exists only to work around this issue: https://github.com/MrSchyzo/tts-rest-wrapper/issues/1#issue-1021557908
 */
@ConfigMapping(prefix = STORAGE_FILESYSTEM)
interface RawStorageConfiguration {
    @WithDefault(STORAGE_FILESYSTEM_ROOT_DEFAULT)
    fun root(): String

    @WithDefault(STORAGE_FILESYSTEM_DEPTH_DEFAULT)
    fun depth(): Long
}
