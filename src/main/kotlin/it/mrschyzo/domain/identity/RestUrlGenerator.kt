package it.mrschyzo.domain.identity

import com.github.michaelbull.result.Result
import io.quarkus.arc.DefaultBean
import it.mrschyzo.configuration.RestUrlGenerationConfiguration
import it.mrschyzo.utils.extensions.catchAsError
import java.net.MalformedURLException
import java.net.URL
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
@DefaultBean
class RestUrlGenerator(
    @Inject val config: RestUrlGenerationConfiguration,
) : UrlGenerator<String, MalformedURLException> {
    override fun generateFrom(item: String): Result<URL, MalformedURLException> =
        catchAsError {
            URL(listOf(config.route(), item).joinToString("/"))
        }
}
