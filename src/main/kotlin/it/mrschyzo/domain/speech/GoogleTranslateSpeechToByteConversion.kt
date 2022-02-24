package it.mrschyzo.domain.speech

import io.smallrye.mutiny.Uni
import it.mrschyzo.web.external.googletranslate.SpeechDownloadService
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.io.InputStream
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("googletranslate")
class GoogleTranslateSpeechToByteConversion(
    @Inject @RestClient val speechClient: SpeechDownloadService,
) : SpeechToByteConversion {
    override fun generateFrom(speechParams: SpeechParams): Uni<InputStream> =
        if (speechParams.isMale) {
            speechClient.generateSpeech(speech = speechParams.text)
                .map { it.readEntity(InputStream::class.java) }
        } else {
            Uni.createFrom().failure { IllegalArgumentException("Female voice is not supported") }
        }
}
