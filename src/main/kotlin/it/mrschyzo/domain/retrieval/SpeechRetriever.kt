package it.mrschyzo.domain.retrieval

import io.smallrye.mutiny.Uni
import java.io.InputStream

interface SpeechRetriever {
    fun retrieve(identifier: String): Uni<InputStream>
}
