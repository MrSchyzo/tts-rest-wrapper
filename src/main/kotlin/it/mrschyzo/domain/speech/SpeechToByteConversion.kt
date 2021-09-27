package it.mrschyzo.domain.speech

import io.smallrye.mutiny.Uni
import java.io.InputStream

interface SpeechToByteConversion {
    fun generateFrom(speechParams: SpeechParams): Uni<InputStream>
}
