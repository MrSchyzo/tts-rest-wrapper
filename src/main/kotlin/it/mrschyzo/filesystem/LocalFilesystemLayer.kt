package it.mrschyzo.filesystem

import com.github.michaelbull.result.Result
import it.mrschyzo.utils.extensions.catchAsError
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
// WARNING: using the filesystem is not atomic, prepare for trouble (and make it double, quot.)
class LocalFilesystemLayer : FilesystemLayer {
    override fun getStreamFrom(filepath: Path): Result<InputStream, IOException> =
        catchAsError {
            FileInputStream(File(filepath.toString()))
        }

    override fun getStreamFrom(filepath: String): Result<InputStream, IOException> =
        getStreamFrom(Path.of(filepath))

    override fun makeDirectories(path: Path): Result<Path, IOException> =
        catchAsError {
            Files.createDirectories(path)
        }

    override fun makeDirectories(path: String): Result<Path, IOException> =
        makeDirectories(Path.of(path))

    override fun writeStreamTo(inputStream: InputStream, filepath: Path): Result<Path, IOException> =
        catchAsError {
            Files.copy(inputStream, filepath, StandardCopyOption.REPLACE_EXISTING)
            filepath
        }

    override fun writeStreamTo(inputStream: InputStream, filepath: String): Result<Path, IOException> =
        writeStreamTo(inputStream, Path.of(filepath))
}
