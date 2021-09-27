package it.mrschyzo.web.exposed.endpoints

import io.smallrye.mutiny.Uni
import it.mrschyzo.domain.retrieval.SpeechRetriever
import it.mrschyzo.domain.speech.SpeechGenerator
import it.mrschyzo.domain.speech.SpeechWebToDomain
import it.mrschyzo.web.exposed.types.Speech
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Named
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/speak")
class SpeechResource(
    @Inject val mapper: SpeechWebToDomain,
    @Inject @Named("simplified") val generator: SpeechGenerator,
    @Inject val retriever: SpeechRetriever,
) {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun generateSpeech(speech: Speech): Uni<String> =
        generator.generateFrom(mapper.webToDomain(speech))

    @Path("{name}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun download(@PathParam("name") name: String): Uni<Response> =
        retriever.retrieve(name).map { it.buildResponse(name) }

    fun InputStream.buildResponse(name: String): Response =
        Response.ok(this, MediaType.APPLICATION_OCTET_STREAM)
            .header("Content-Disposition", "attachment; filename=\"$name\"")
            .build()
}
