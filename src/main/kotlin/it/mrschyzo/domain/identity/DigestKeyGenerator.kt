package it.mrschyzo.domain.identity

import it.mrschyzo.domain.speech.SpeechParams
import java.security.MessageDigest
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class DigestKeyGenerator(
    @Inject val retrieveDigest: () -> MessageDigest,
    @Inject val byteToString: (ByteArray) -> String
) : KeyGenerator<SpeechParams, String> {
    override fun generateFrom(input: SpeechParams): String =
        byteToString(retrieveDigest().digest("${input.speaker}_${input.text}".toByteArray()))
}
