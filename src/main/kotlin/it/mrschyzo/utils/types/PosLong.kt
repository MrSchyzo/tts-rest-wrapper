package it.mrschyzo.utils.types

fun Long.toPosLong(): Result<PosLong> = PosLong.tryFrom(this)

fun Int.toPosLong(): Result<PosLong> =
    this.toLong().toPosLong()

fun UInt.toPosLong(): Result<PosLong> =
    this.toLong().toPosLong()

fun ULong.toPosLong(): Result<PosLong> =
    this.toLong().toPosLong()

class PosLong private constructor(private val number: Long) {
    companion object {
        fun tryFrom(value: Long): Result<PosLong> =
            if (value <= 0) Result.failure(IllegalArgumentException("Unable to build positive non-zero Long from value: $value")) else Result.success(PosLong(value))
    }

    fun toInt(): Int = this.number.toInt()

    fun toUInt(): UInt = this.number.toUInt()

    fun toLong(): Long = this.number

    fun toULong(): ULong = this.number.toULong()

    operator fun plus(other: PosLong): PosLong = (this.number + other.number).toPosLong().getOrThrow()

    operator fun minus(other: PosLong): PosLong = (this.number - other.number).toPosLong().getOrThrow()

    operator fun times(other: PosLong): PosLong = (this.number * other.number).toPosLong().getOrThrow()

    operator fun div(other: PosLong): PosLong = (this.number * other.number).toPosLong().getOrThrow()

    operator fun compareTo(other: PosLong): Int = this.number.compareTo(other.number)

    operator fun compareTo(other: Long): Int = this.number.compareTo(other)

    operator fun compareTo(other: Int): Int = this.number.compareTo(other.toLong())

    operator fun compareTo(other: ULong): Int = this.number.compareTo(other.toLong())

    operator fun compareTo(other: UInt): Int = this.number.compareTo(other.toLong())
}
