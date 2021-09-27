package it.mrschyzo.domain

import it.mrschyzo.domain.speech.SpeechParams
import java.util.Locale
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ItalianSpeechSimplifier : SpeechSimplifier {
    override fun simplify(params: SpeechParams): SpeechParams =
        SpeechParams(
            text = params.text.lowercase(Locale.ITALY)
                .replace(Regex("[^a-zàèéìòù ]"), " ")
                .replace(Regex("([a-zàèéìòù])\\1{3,}")) {
                    it.value.substring(0, 2)
                }.replace(Regex("( )\\1{2,}")) {
                    it.value.substring(0, 1)
                },
            isMale = params.isMale
        )
}
