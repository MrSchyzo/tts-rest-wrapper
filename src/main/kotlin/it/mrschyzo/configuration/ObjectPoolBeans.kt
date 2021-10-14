package it.mrschyzo.configuration

import io.quarkus.arc.DefaultBean
import it.mrschyzo.utils.concurrency.BlockingLeasing
import it.mrschyzo.utils.concurrency.EagerBlockingLeasing
import java.security.MessageDigest
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Inject

/***
 * This configuration class exists only to work around this issue: https://github.com/MrSchyzo/tts-rest-wrapper/issues/1#issue-1021557908
 */
@ApplicationScoped
class ObjectPoolBeans(
    @Inject val objectPoolConfiguration: ObjectPoolConfiguration,
    @Inject val digestGenerator: () -> MessageDigest,
) {
    @Produces
    @DefaultBean
    fun digestPool(): BlockingLeasing<MessageDigest> =
        EagerBlockingLeasing(objectPoolConfiguration.size(), digestGenerator)
}
