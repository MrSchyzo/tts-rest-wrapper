package it.mrschyzo.domain.caching

import com.github.michaelbull.result.map
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.infrastructure.Infrastructure
import it.mrschyzo.configuration.StorageConfiguration
import it.mrschyzo.filesystem.FilesystemLayer
import it.mrschyzo.filesystem.PartialPathDecider
import it.mrschyzo.utils.extensions.and
import it.mrschyzo.utils.extensions.onErrorThrow
import java.io.FileNotFoundException
import java.io.InputStream
import java.nio.file.Path
import java.util.function.Supplier
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class FilesystemLazyCache(
    @Inject val config: StorageConfiguration,
    @Inject val filesystemLayer: FilesystemLayer,
    @Inject val partialPathDecider: PartialPathDecider
) : LazyCache<String, InputStream> {
    override fun getOr(key: String, lazyInitializer: () -> Uni<InputStream>): Uni<InputStream> =
        get("$key.mp3")
            .onFailure(FileNotFoundException::class.java)
            .recoverWithUni(Supplier(lazyInitializer))
            .flatMap {
                putAndGet("$key.mp3", it)
            }

    // Implementation details

    private fun get(filename: String): Uni<InputStream> =
        Uni.createFrom()
            .item { getStreamFrom(filename) }
            .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())

    private fun putAndGet(filename: String, stream: InputStream): Uni<InputStream> =
        Uni.createFrom()
            .item(stream)
            .map { writeStreamTo(it, filename) }
            .flatMap(this::get)
            .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())

    private fun writeStreamTo(inputStream: InputStream, filename: String): String =
        inputStream.use { filesystemLayer.writeStreamTo(it, ensurePathOf(filename)) }
            .map { filename }
            .onErrorThrow()

    private fun getStreamFrom(filename: String): InputStream =
        this.filesystemLayer
            .getStreamFrom(ensurePathOf(filename))
            .onErrorThrow()

    private fun ensurePathOf(filename: String): Path {
        val subPath = partialPathDecider.decideFor(filename).onErrorThrow()
        val filepath =
            config.root()
                .and(subPath)
                .and(filename)

        return filesystemLayer
            .makeDirectories(filepath.parent)
            .map { filepath }
            .onErrorThrow()
    }
}
