package it.mrschyzo.configuration

import org.eclipse.microprofile.config.spi.Converter
import java.net.URL

class StringToURLConverter : Converter<URL> {
    override fun convert(value: String?): URL =
        URL(value ?: "http://localhost:8080")
}
