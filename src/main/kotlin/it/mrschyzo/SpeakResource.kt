package it.mrschyzo

import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/speak")
class SpeakResource(
    @Inject @RestClient val speechClient: SpeechService,
    @Inject @RestClient val downloadClient: DownloadService
) {
    @Path("url")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun getPathOfSpeech(speech: Speech): String =
        speechClient.generateSpeech(msg = speech.text).url

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun speak(speech: Speech): Uni<ByteArray> {
        val speechFile = speechClient.generateSpeech(msg = speech.text).mp3

        return downloadClient.downloadFile(speechFile)
    }
}