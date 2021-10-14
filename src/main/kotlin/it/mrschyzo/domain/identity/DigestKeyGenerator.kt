package it.mrschyzo.domain.identity

import it.mrschyzo.domain.speech.SpeechParams
import java.security.MessageDigest
import java.util.concurrent.BlockingQueue
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class DigestKeyGenerator(
    @Inject val digestPool: BlockingQueue<MessageDigest>,
    @Inject val byteToString: (ByteArray) -> String
) : KeyGenerator<SpeechParams, String> {
    override fun generateFrom(input: SpeechParams): String {
        val digest = digestPool.take()
        try {
            return byteToString(
                digest.digest("${input.speaker}_${input.text}".toByteArray())
            )
        } finally {
            digestPool.put(digest)
        }
    }
}
