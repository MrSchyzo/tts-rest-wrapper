package it.mrschyzo.web.external.endpoints

import io.smallrye.mutiny.Uni
import it.mrschyzo.web.external.endpoints.configuration.GenerateSpeechClientHeadersFactory
import it.mrschyzo.web.external.types.SpeechResult
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.Consumes
import javax.ws.rs.FormParam
import javax.ws.rs.POST
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RegisterRestClient(configKey = "ttsmp3-client")
@RegisterClientHeaders(GenerateSpeechClientHeadersFactory::class)
interface SpeechService {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    fun generateSpeech(
        @FormParam("msg") msg: String,
        @FormParam("lang") lang: String = "Giorgio",
        @FormParam("source") source: String = "ttsmp3"
    ): Uni<SpeechResult>
}