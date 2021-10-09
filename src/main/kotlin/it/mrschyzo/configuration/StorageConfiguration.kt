package it.mrschyzo.configuration

import it.mrschyzo.utils.types.PositiveLong
import java.nio.file.Path

interface StorageConfiguration {
    fun root(): Path

    fun depth(): PositiveLong
}
