package it.mrschyzo.domain.retrieval

import io.smallrye.mutiny.Uni
import it.mrschyzo.domain.caching.LazyCache
import it.mrschyzo.domain.errors.SpeechNotFoundException
import java.io.InputStream
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class CachedSpeechRetriever(
    @Inject val cache: LazyCache<String, InputStream>
) : SpeechRetriever {
    override fun retrieve(identifier: String): Uni<InputStream> = cache.getOr(identifier) {
        throw SpeechNotFoundException("Unable to locate $identifier in cache")
    }
}
