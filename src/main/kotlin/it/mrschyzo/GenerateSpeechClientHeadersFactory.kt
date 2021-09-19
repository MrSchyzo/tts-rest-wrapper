package it.mrschyzo

import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.core.MultivaluedHashMap
import javax.ws.rs.core.MultivaluedMap

@ApplicationScoped
class GenerateSpeechClientHeadersFactory: ClientHeadersFactory {
    override fun update(
        incomingHeaders: MultivaluedMap<String, String>?,
        outgoingHeaders: MultivaluedMap<String, String>?
    ): MultivaluedMap<String, String> =
        MultivaluedHashMap(
            hashMapOf(
                Pair("Connection", "keep-alive"),
                Pair("Content-type", "application/x-www-form-urlencoded"),
                Pair("Accept", "*/*"),
                Pair("Origin", "https://ttsmp3.com"),
                Pair("Sec-Fetch-Site", "same-origin"),
                Pair("Sec-Fetch-Mode", "cors"),
                Pair("Sec-Fetch-Dest", "empty"),
                Pair("Referer", "https://ttsmp3.com/"),
                Pair("Accept-Language", "it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7"),
            )
        )
}
