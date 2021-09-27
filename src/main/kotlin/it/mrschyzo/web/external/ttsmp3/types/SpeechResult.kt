package it.mrschyzo.web.external.ttsmp3.types

import com.fasterxml.jackson.annotation.JsonProperty

data class SpeechResult(
    @JsonProperty("Cached") val cached: Int,
    @JsonProperty("Error") val error: Int,
    @JsonProperty("MP3") val mp3: String,
    @JsonProperty("Speaker") val speaker: String,
    @JsonProperty("Text") val text: String,
    @JsonProperty("URL") val url: String,
    @JsonProperty("tasktype") val taskType: String
)
