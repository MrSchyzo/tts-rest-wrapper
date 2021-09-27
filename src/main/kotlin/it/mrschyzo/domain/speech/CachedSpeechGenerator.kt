package it.mrschyzo.domain.speech

import io.smallrye.mutiny.Uni
import it.mrschyzo.domain.caching.LazyCache
import it.mrschyzo.domain.identity.KeyGenerator
import java.io.InputStream
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("cached")
class CachedSpeechGenerator(
    @Inject val converter: SpeechToByteConversion,
    @Inject val cache: LazyCache<String, InputStream>,
    @Inject val keyGenerator: KeyGenerator<SpeechParams, String>,
) : SpeechGenerator {
    override fun generateFrom(params: SpeechParams): Uni<String> {
        val key = keyGenerator.generateFrom(params)

        return cache.getOr(key) {
            converter.generateFrom(params)
        }.map {
            key
        }
    }
}
