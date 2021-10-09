package it.mrschyzo.filesystem

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import it.mrschyzo.configuration.StorageConfiguration
import it.mrschyzo.utils.extensions.and
import java.nio.file.Path
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class NestedDirsPathDecider(
    @Inject val config: StorageConfiguration,
) : PathDecider {
    override fun decideFor(filename: String): Result<Path, IllegalArgumentException> {
        val withoutExtension = filename.substringBeforeLast(".")
        val integerDepth = config.depth()

        return if (integerDepth > withoutExtension.length) {
            Err(IllegalArgumentException("$filename is too short."))
        } else {
            val initialPath = Path.of("${withoutExtension[0]}")
            Ok(
                withoutExtension
                    .asSequence()
                    .take(integerDepth.toInt())
                    .drop(1)
                    .map { "$it" }
                    .fold(initialPath, Path::and)
            )
        }
    }
}
