package it.mrschyzo.domain.identity

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import it.mrschyzo.configuration.UrlGenerationConfiguration
import it.mrschyzo.filesystem.PartialPathDecider
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

// It will be used when I decide to delegate the file download to another server/service (e.g. dedicated Nginx, CDN, S3, etc.)
class WebFileUrlGenerator(
    @Inject val partialPathDecider: PartialPathDecider,
    @Inject val config: UrlGenerationConfiguration,
) : UrlGenerator<String, MalformedURLException> {
    override fun generateFrom(item: String): Result<URL, MalformedURLException> {
        val fileNodeName = "$item${config.urlSuffix()}"
        return partialPathDecider.decideFor(fileNodeName)
            .map { listOf(config.serveRoot().toString(), it, fileNodeName).map { s -> s.trim('/') } }
            .map { it.joinToString("/") }
            .map { URL(it) }
            .mapError { MalformedURLException("Malformed URL: ${it.message}") }
    }
}
