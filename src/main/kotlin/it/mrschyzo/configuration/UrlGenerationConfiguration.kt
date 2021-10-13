package it.mrschyzo.configuration

import java.net.URL

interface UrlGenerationConfiguration {
    fun serveRoot(): URL

    fun urlSuffix(): String
}
