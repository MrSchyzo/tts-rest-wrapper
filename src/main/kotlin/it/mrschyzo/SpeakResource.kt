package it.mrschyzo

import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.openapi.models.headers.Header
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/speak")
class SpeakResource(
    @Inject @RestClient val speechClient: SpeechService,
    @Inject @RestClient val downloadClient: DownloadService,
    @Inject @RestClient val bigFileClient: BigFileService,
) {
    @Path("url")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun getPathOfSpeech(speech: Speech): String =
        speechClient.generateSpeech(speech.text).url

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun speak(speech: Speech): Uni<ByteArray> =
        downloadClient.downloadFile(speechClient.generateSpeech(speech.text).mp3)

    /**
     * This is a dumb example to test big (bigger than what you'd want to store in memory) files transfers.
     * It looks it works with -Xmx16M
     */
    @Path("big/{file}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun getBig(@PathParam("file") file: String) : Uni<Response> {
        val filename = "${file}.zip"
        return bigFileClient.downloadFile(filename).onItem().transform {
            it.readEntity(InputStream::class.java)
        }.onItem().invoke { inputStream ->
            Files.copy(inputStream, File(filename).toPath(), StandardCopyOption.REPLACE_EXISTING)
        }.onItem().transform {
            File(filename)
        }.onItem().transform {
            Response.ok(it, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"${file}.bin\"")
                .build()
        }
    }
}