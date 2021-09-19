package it.mrschyzo

/***
 * An inline class is serialized as a simple json string
 *
 * You do not have to specify this class as {"text": "..."}, but you need to specify it just as "..."
 */
@JvmInline
value class Speech(val text: String)