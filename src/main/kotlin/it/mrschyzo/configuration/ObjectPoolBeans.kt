package it.mrschyzo.configuration

import io.quarkus.arc.DefaultBean
import java.security.MessageDigest
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
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
    fun digestPool(): BlockingQueue<MessageDigest> {
        val pool = ArrayBlockingQueue<MessageDigest>(objectPoolConfiguration.size())
        while (pool.offer(digestGenerator())) ;
        return pool
    }
}
