package it.mrschyzo.domain.caching

import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.infrastructure.Infrastructure
import it.mrschyzo.configuration.StorageConfiguration
import it.mrschyzo.utils.types.PosLong
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.lang.IllegalArgumentException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.function.Supplier
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

// TODO: Refactor this shit.
@ApplicationScoped
class StorageLazyCache(
    @Inject val config: StorageConfiguration,
) : LazyCache<String, InputStream> {
    override fun getOr(key: String, lazyInitializer: () -> Uni<InputStream>): Uni<InputStream> {
        val filename = "$key.mp3"
        return get(filename)
            .onFailure(FileNotFoundException::class.java)
            .recoverWithUni(Supplier(lazyInitializer))
            .flatMap {
                putAndGet(filename, it)
            }
    }

    private fun get(filename: String): Uni<InputStream> =
        Uni.createFrom()
            .item { streamFile(filename) }
            .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())

    private fun putAndGet(filename: String, stream: InputStream): Uni<InputStream> =
        Uni.createFrom()
            .item(stream)
            .map { saveFile(it, filename) }
            .flatMap(this::get)
            .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())

    private fun saveFile(it: InputStream, filename: String): String {
        it.use { inputStream -> Files.copy(inputStream, ensurePathOf(filename), StandardCopyOption.REPLACE_EXISTING) }
        return filename
    }

    /**
     * @throws FileNotFoundException
     */
    private fun streamFile(filename: String): InputStream {
        val filepath = ensurePathOf(filename)

        return if (Files.isReadable(filepath))
            FileInputStream(File(filepath.toString()))
        else
            throw FileNotFoundException("$filename was not found")
    }

    private fun ensurePathOf(filename: String): Path {
        val filepath = getPathOf(filename)

        Files.createDirectories(filepath.parent)

        return filepath
    }

    private fun getPathOf(filename: String) =
        Path.of(
            config.root().toString(),
            deconstructIntoDirectories(filename).toString(),
            filename
        )

    private fun deconstructIntoDirectories(filename: String, depth: PosLong? = null): Path {
        val withoutExtension = filename.substringBeforeLast(".")
        val integerDepth = (depth ?: config.depth())

        if (integerDepth > withoutExtension.length) {
            throw IllegalArgumentException("$filename is too short.")
        }

        return withoutExtension
            .asSequence()
            .take(integerDepth.toInt())
            .drop(1)
            .map { "$it" }
            .fold(Path.of("${withoutExtension[0]}")) {
                path, dir ->
                Path.of(path.toString(), dir)
            }
    }
}
