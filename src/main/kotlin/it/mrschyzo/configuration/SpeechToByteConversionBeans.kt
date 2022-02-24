package it.mrschyzo.configuration

import it.mrschyzo.domain.speech.SpeechToByteConversion
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
class SpeechToByteConversionBeans(
    @Inject @Named("ttsmp3") val ttsmp3Conversion: SpeechToByteConversion,
    @Inject @Named("googletranslate") val googleConversion: SpeechToByteConversion,
) {
    @Produces
    @Named("s2bConversions")
    fun cascadingConversions(): List<SpeechToByteConversion> =
        listOf(googleConversion, ttsmp3Conversion)
}
