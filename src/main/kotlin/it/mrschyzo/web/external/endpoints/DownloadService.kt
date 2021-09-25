package it.mrschyzo.web.external.endpoints

import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@RegisterRestClient(configKey = "ttsmp3-downloader")
interface DownloadService {
    @Path("{filename}")
    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun downloadFile(
        @PathParam("filename") filename: String
    ): Uni<Response>
}