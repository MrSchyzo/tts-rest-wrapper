package it.mrschyzo.domain.speech

import it.mrschyzo.web.exposed.types.Speech

interface SpeechWebToDomain {
    fun webToDomain(web: Speech): SpeechParams
}
