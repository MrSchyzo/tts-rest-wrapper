package it.mrschyzo.utils.concurrency

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

/**
 * This is the inlined version of EagerBlockLeasing. It's ugly to use, but it's more optimized.
 */
class EagerBlockingLeasing<T>(
    size: Int = 1,
    generator: () -> T,
) : BlockingLeasing<T> {
    private val pool: BlockingQueue<T>

    init {
        pool = ArrayBlockingQueue(size, true)
        while (pool.offer(generator())) ;
    }

    override fun <V> borrow(block: (T) -> V): V {
        val element: T = pool.take()
        try {
            return block(element)
        } finally {
            pool.put(element)
        }
    }
}
