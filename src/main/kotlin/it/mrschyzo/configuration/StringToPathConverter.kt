package it.mrschyzo.configuration

import org.eclipse.microprofile.config.spi.Converter
import java.nio.file.Path

class StringToPathConverter : Converter<Path> {
    override fun convert(value: String?): Path =
        Path.of(value ?: ".")
}
