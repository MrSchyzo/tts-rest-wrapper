package it.mrschyzo

data class SpeechResult(
    val Cached: Int,
    val Error: Int,
    val MP3: String,
    val Speaker: String,
    val Text: String,
    val URL: String,
    val tasktype: String
)