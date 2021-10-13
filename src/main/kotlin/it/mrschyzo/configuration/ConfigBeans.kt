package it.mrschyzo.configuration

import io.quarkus.arc.DefaultBean
import it.mrschyzo.utils.types.PositiveLong
import java.net.URL
import java.nio.file.Path
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Inject

/***
 * This configuration class exists only to work around this issue: https://github.com/MrSchyzo/tts-rest-wrapper/issues/1#issue-1021557908
 */
@ApplicationScoped
class ConfigBeans(
    @Inject val storageConfiguration: RawStorageConfiguration,
    @Inject val urlGenerationConfiguration: RawUrlGenerationConfiguration,
    @Inject val restUrlGenerationConfiguration: RawRestUrlGenerationConfiguration,
) {
    @Produces
    @DefaultBean
    fun storageConfig(): StorageConfiguration {
        val root = StringToPathConverter().convert(storageConfiguration.root())
        val depth = PositiveLongCoercionConverter().convert(storageConfiguration.depth().toString())

        return object : StorageConfiguration {
            override fun root(): Path = root
            override fun depth(): PositiveLong = depth
        }
    }

    @Produces
    @DefaultBean
    fun urlGenerationConfig(): UrlGenerationConfiguration {
        val serveRoot = StringToURLConverter().convert(urlGenerationConfiguration.serveRoot())
        val urlSuffix = urlGenerationConfiguration.urlSuffix()

        return object : UrlGenerationConfiguration {
            override fun serveRoot(): URL = serveRoot
            override fun urlSuffix(): String = urlSuffix
        }
    }

    @Produces
    @DefaultBean
    fun restUrlGenerationConfig(): RestUrlGenerationConfiguration {
        val route = StringToURLConverter().convert(restUrlGenerationConfiguration.route())

        return object : RestUrlGenerationConfiguration {
            override fun route(): URL = route
        }
    }
}
