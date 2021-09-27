package it.mrschyzo.configuration

import io.quarkus.arc.DefaultBean
import java.security.MessageDigest
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.xml.bind.DatatypeConverter

@ApplicationScoped
class DigestBeans {
    @Produces
    @DefaultBean
    fun sha256(): MessageDigest =
        MessageDigest.getInstance("SHA-256")

    @Produces
    @DefaultBean
    fun byteToString(): (ByteArray) -> String =
        DatatypeConverter::printHexBinary
}
