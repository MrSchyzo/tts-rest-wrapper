package it.mrschyzo.domain.speech

import io.smallrye.mutiny.Uni
import it.mrschyzo.domain.SpeechSimplifier
import java.net.URL
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("simplified")
class SimplifiedSpeechGenerator(
    @Inject @Named("cached") val generator: SpeechGenerator,
    @Inject val simplifier: SpeechSimplifier,
) : SpeechGenerator {
    override fun generateFrom(params: SpeechParams): Uni<URL> = generator.generateFrom(simplifier.simplify(params))
}
