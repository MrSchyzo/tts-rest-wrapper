package it.mrschyzo

import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/speak")
class SpeakResource(
    @Inject @RestClient val speechClient: SpeechService
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun speak(speech: Speech): String =
        speechClient.generateSpeech(msg = speech.text).URL
}