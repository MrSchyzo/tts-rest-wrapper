package it.mrschyzo.domain.speech

import io.smallrye.mutiny.Uni
import java.net.URL

interface SpeechGenerator {
    fun generateFrom(params: SpeechParams): Uni<URL>
}
