package it.mrschyzo.domain.identity

import java.security.MessageDigest
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class DigestKeyGenerator(
    @Inject val digest: MessageDigest,
    @Inject val byteToString: (ByteArray) -> String
) : KeyGenerator<String, String> {
    override fun generateFrom(input: String): String =
        byteToString(digest.digest(input.toByteArray()))
}