package it.mrschyzo.filesystem

import com.github.michaelbull.result.Result

interface PartialPathDecider {
    fun decideFor(filename: String): Result<String, IllegalArgumentException>
}
