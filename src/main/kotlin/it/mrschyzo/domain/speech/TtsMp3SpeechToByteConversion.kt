package it.mrschyzo.domain.speech

import io.smallrye.mutiny.Uni
import it.mrschyzo.web.external.ttsmp3.endpoints.DownloadService
import it.mrschyzo.web.external.ttsmp3.endpoints.SpeechService
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.io.InputStream
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("ttsmp3")
class TtsMp3SpeechToByteConversion(
    @Inject @RestClient val speechClient: SpeechService,
    @Inject @RestClient val downloadClient: DownloadService,
) : SpeechToByteConversion {
    override fun generateFrom(speechParams: SpeechParams): Uni<InputStream> =
        speechClient.generateSpeech(speechParams.text, lang = speechParams.speaker)
            .map { it.mp3 }
            .flatMap { downloadClient.downloadFile(it) }
            .map { it.readEntity(InputStream::class.java) }
}
