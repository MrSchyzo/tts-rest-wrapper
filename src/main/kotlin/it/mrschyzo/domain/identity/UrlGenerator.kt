package it.mrschyzo.domain.identity

import com.github.michaelbull.result.Result
import java.net.URL

interface UrlGenerator<in T, out E> where E : Exception {
    fun generateFrom(item: T): Result<URL, E>
}
