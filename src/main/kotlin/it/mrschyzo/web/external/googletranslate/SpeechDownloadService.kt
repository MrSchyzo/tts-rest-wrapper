package it.mrschyzo.web.external.googletranslate

import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

// https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&q=$*&tl=it
@ApplicationScoped
@RegisterRestClient(configKey = "googletranslate-client")
interface SpeechDownloadService {
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun generateSpeech(
        @QueryParam("ie") encoding: String = "UTF-8",
        @QueryParam("client") client: String = "tw-ob",
        @QueryParam("tl") language: String = "it",
        @QueryParam("q") speech: String
    ): Uni<Response>
}
