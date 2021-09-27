package it.mrschyzo.domain

import it.mrschyzo.domain.speech.SpeechParams

interface SpeechSimplifier {
    fun simplify(params: SpeechParams): SpeechParams
}
