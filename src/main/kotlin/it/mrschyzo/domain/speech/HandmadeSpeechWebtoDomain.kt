package it.mrschyzo.domain.speech

import it.mrschyzo.web.exposed.types.Speech
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class HandmadeSpeechWebtoDomain : SpeechWebToDomain {
    override fun webToDomain(web: Speech): SpeechParams =
        SpeechParams(
            text = web.text,
            isMale = web.isMale
        )
}
