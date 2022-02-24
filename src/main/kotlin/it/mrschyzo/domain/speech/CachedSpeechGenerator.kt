package it.mrschyzo.domain.speech

import io.smallrye.mutiny.Uni
import it.mrschyzo.domain.caching.LazyCache
import it.mrschyzo.domain.identity.KeyGenerator
import it.mrschyzo.domain.identity.UrlGenerator
import it.mrschyzo.utils.extensions.onErrorThrow
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named("cached")
class CachedSpeechGenerator(
    @Inject @Named("cascading") val converter: SpeechToByteConversion,
    @Inject val cache: LazyCache<String, InputStream>,
    @Inject val keyGenerator: KeyGenerator<SpeechParams, String>,
    @Inject val urlGenerator: UrlGenerator<String, MalformedURLException>,
) : SpeechGenerator {
    override fun generateFrom(params: SpeechParams): Uni<URL> {
        val key = keyGenerator.generateFrom(params)

        return cache.getOr(key) {
            converter.generateFrom(params)
        }.map {
            it.close()
            urlGenerator.generateFrom(key).onErrorThrow()
        }
    }
}
