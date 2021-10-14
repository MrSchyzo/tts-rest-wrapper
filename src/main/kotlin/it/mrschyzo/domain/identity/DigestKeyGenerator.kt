package it.mrschyzo.domain.identity

import it.mrschyzo.domain.speech.SpeechParams
import it.mrschyzo.utils.concurrency.BlockingLeasing
import java.security.MessageDigest
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class DigestKeyGenerator(
    @Inject val retrieveDigest: BlockingLeasing<MessageDigest>,
    @Inject val byteToString: (ByteArray) -> String
) : KeyGenerator<SpeechParams, String> {
    override fun generateFrom(input: SpeechParams): String =
        byteToString(
            retrieveDigest.borrow {
                it.digest("${input.speaker}_${input.text}".toByteArray())
            }
        )
}
