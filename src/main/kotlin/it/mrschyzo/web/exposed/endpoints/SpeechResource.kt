package it.mrschyzo.web.exposed.endpoints

import io.smallrye.mutiny.Uni
import it.mrschyzo.domain.TextSimplifier
import it.mrschyzo.domain.caching.LazyCache
import it.mrschyzo.domain.identity.KeyGenerator
import it.mrschyzo.web.exposed.types.Speech
import it.mrschyzo.web.external.endpoints.DownloadService
import it.mrschyzo.web.external.endpoints.SpeechService
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.io.InputStream
import java.lang.RuntimeException
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

//TODO: endpoints must not contain any business logic: refactor!
@Path("/speak")
class SpeechResource(
    @Inject @RestClient val speechClient: SpeechService,
    @Inject @RestClient val downloadClient: DownloadService,
    @Inject val cache: LazyCache<String, InputStream>,
    @Inject val simplifier: TextSimplifier,
    @Inject val keyGenerator: KeyGenerator<String, String>,
) {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun generateSpeech(speech: Speech): Uni<String> {
        val simplifiedSpeech = Speech(simplifier.simplify(speech.text))
        val key = "${keyGenerator.generateFrom(simplifiedSpeech.text)}.mp3"

        return cache.getOr(key) {
            speechClient.generateSpeech(simplifiedSpeech.text)
                .map { it.mp3 }
                .flatMap { downloadClient.downloadFile(it) }
                .map { it.readEntity(InputStream::class.java) }
        }.map {
            key
        }
    }

    @Path("{name}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun getFile(@PathParam("name") name: String): Uni<Response> = cache.getOr(name) {
            throw RuntimeException("NOT FOUND")
        }.map {
            Response.ok(it, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"$name\"")
                .build()
        }
}