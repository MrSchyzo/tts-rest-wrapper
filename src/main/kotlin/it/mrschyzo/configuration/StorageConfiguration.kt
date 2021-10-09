package it.mrschyzo.configuration

import it.mrschyzo.utils.types.PosLong
import java.nio.file.Path

interface StorageConfiguration {
    fun root(): Path

    fun depth(): PosLong
}
