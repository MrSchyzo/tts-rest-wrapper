package it.mrschyzo.configuration

import java.net.URL

interface RestUrlGenerationConfiguration {
    fun route(): URL
}
