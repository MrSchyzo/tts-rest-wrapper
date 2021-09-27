package it.mrschyzo.domain.caching

import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.infrastructure.Infrastructure
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.function.Supplier
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class StorageLazyCache : LazyCache<String, InputStream> {
    override fun getOr(key: String, lazyInitializer: () -> Uni<InputStream>): Uni<InputStream> {
        val filename = "$key.mp3"
        return get(filename)
            .onFailure(FileNotFoundException::class.java)
            .recoverWithUni(Supplier(lazyInitializer))
            .flatMap {
                putAndGet(filename, it)
            }
    }

    private fun get(key: String): Uni<InputStream> =
        Uni.createFrom()
            .item { streamFile(key) }
            .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())

    private fun putAndGet(key: String, stream: InputStream): Uni<InputStream> =
        Uni.createFrom()
            .item(stream)
            .map { saveFile(it, key) }
            .flatMap(this::get)
            .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())

    private fun saveFile(it: InputStream, key: String): String {
        it.use { inputStream -> Files.copy(inputStream, Path.of(key), StandardCopyOption.REPLACE_EXISTING) }
        return key
    }

    /**
     * @throws FileNotFoundException
     */
    private fun streamFile(key: String): InputStream =
        if (Files.isReadable(Path.of(key)))
            FileInputStream(File(key))
        else
            throw FileNotFoundException("$key was not found")
}
