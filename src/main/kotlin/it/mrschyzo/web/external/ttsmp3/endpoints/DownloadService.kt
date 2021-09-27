package it.mrschyzo.web.external.ttsmp3.endpoints

import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
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
