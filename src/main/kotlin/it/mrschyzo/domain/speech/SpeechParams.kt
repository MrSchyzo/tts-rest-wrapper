package it.mrschyzo.domain.speech

data class SpeechParams(val text: String, val isMale: Boolean) {
    val speaker: String = if (isMale) "Giorgio" else "Bianca"
}
