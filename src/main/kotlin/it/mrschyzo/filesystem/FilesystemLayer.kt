package it.mrschyzo.filesystem

import com.github.michaelbull.result.Result
import java.io.IOException
import java.io.InputStream
import java.nio.file.Path

interface FilesystemLayer {
    fun getStreamFrom(filepath: Path): Result<InputStream, IOException>

    fun getStreamFrom(filepath: String): Result<InputStream, IOException>

    fun makeDirectories(path: Path): Result<Path, IOException>

    fun makeDirectories(path: String): Result<Path, IOException>

    fun writeStreamTo(inputStream: InputStream, filepath: Path): Result<Path, IOException>

    fun writeStreamTo(inputStream: InputStream, filepath: String): Result<Path, IOException>
}
