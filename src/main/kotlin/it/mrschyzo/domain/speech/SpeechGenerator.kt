package it.mrschyzo.domain.speech

import io.smallrye.mutiny.Uni

interface SpeechGenerator {
    fun generateFrom(params: SpeechParams): Uni<String>
}
