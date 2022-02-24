package it.mrschyzo.domain.speech

import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import java.io.InputStream
import javax.annotation.Priority
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.inject.Named

@Priority(1)
@ApplicationScoped
@Named("cascading")
@Default
class CascadingSpeechToByteConversion(
    @Inject @Named("s2bConversions") val conversions: List<SpeechToByteConversion>,
) : SpeechToByteConversion {
    companion object {
        private const val ERROR_MESSAGE = "Conversions list provided is empty, at least a conversion is needed!"
    }

    override fun generateFrom(speechParams: SpeechParams): Uni<InputStream> =
        conversions.fold(Uni.createFrom().failure { IllegalStateException(ERROR_MESSAGE) }) {
            current, nextConversion ->
            current.onFailure().recoverWithUni { error ->
                if (error !is IllegalStateException)
                    Log.warn("Failure while converting speech $speechParams: $error")

                nextConversion.generateFrom(speechParams)
            }
        }
}
