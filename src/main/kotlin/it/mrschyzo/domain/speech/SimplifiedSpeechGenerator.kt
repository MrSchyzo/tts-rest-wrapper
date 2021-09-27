package it.mrschyzo.domain.speech

import io.smallrye.mutiny.Uni
import it.mrschyzo.domain.SpeechSimplifier
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("simplified")
class SimplifiedSpeechGenerator(
    @Inject @Named("cached") val generator: SpeechGenerator,
    @Inject val simplifier: SpeechSimplifier,
) : SpeechGenerator {
    override fun generateFrom(params: SpeechParams): Uni<String> = generator.generateFrom(simplifier.simplify(params))
}
