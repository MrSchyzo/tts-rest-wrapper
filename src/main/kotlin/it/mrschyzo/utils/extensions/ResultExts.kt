package it.mrschyzo.utils.extensions

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

inline fun <O, reified E : Throwable> catchAsError(block: () -> O): Result<O, E> =
    try {
        Ok(block())
    } catch (e: Throwable) {
        when (e) {
            is E -> Err(e)
            else -> throw RuntimeException("Unexpected exception type inside `asResult` block evaluation", e)
        }
    }

fun <O, E : Throwable> Result<O, E>.onErrorThrow(): O =
    when (this) {
        is Ok -> this.value
        is Err -> throw this.error
    }
