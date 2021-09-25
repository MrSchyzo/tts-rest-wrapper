package it.mrschyzo.domain.caching

import io.smallrye.mutiny.Uni

interface LazyCache<IN, OUT> where IN : Comparable<IN> {
    fun getOr(key: IN, lazyInitializer: () -> Uni<OUT>): Uni<OUT>
}