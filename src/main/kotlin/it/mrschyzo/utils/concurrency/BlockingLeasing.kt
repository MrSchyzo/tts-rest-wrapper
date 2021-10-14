package it.mrschyzo.utils.concurrency

// Why is this signature covariant? Is contravariance a "negation"?
interface BlockingLeasing<out T> {
    fun <V> borrow(block: (T) -> V): V
}
