package it.mrschyzo.filesystem

import com.github.michaelbull.result.Result
import java.nio.file.Path

interface PathDecider {
    fun decideFor(filename: String): Result<Path, IllegalArgumentException>
}
