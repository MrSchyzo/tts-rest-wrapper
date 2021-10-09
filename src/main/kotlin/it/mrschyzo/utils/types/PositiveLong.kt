package it.mrschyzo.utils.types

fun Long.toPosLong(): Result<PositiveLong> = PositiveLong.tryFrom(this)

fun Int.toPosLong(): Result<PositiveLong> =
    this.toLong().toPosLong()

fun UInt.toPosLong(): Result<PositiveLong> =
    this.toLong().toPosLong()

fun ULong.toPosLong(): Result<PositiveLong> =
    this.toLong().toPosLong()

class PositiveLong private constructor(private val number: Long) {
    companion object {
        fun tryFrom(value: Long): Result<PositiveLong> =
            if (value <= 0) Result.failure(IllegalArgumentException("Unable to build positive non-zero Long from value: $value")) else Result.success(PositiveLong(value))
    }

    fun toInt(): Int = this.number.toInt()

    fun toUInt(): UInt = this.number.toUInt()

    fun toLong(): Long = this.number

    fun toULong(): ULong = this.number.toULong()

    operator fun plus(other: PositiveLong): PositiveLong = (this.number + other.number).toPosLong().getOrThrow()

    operator fun minus(other: PositiveLong): PositiveLong = (this.number - other.number).toPosLong().getOrThrow()

    operator fun times(other: PositiveLong): PositiveLong = (this.number * other.number).toPosLong().getOrThrow()

    operator fun div(other: PositiveLong): PositiveLong = (this.number * other.number).toPosLong().getOrThrow()

    operator fun compareTo(other: PositiveLong): Int = this.number.compareTo(other.number)

    operator fun compareTo(other: Long): Int = this.number.compareTo(other)

    operator fun compareTo(other: Int): Int = this.number.compareTo(other.toLong())

    operator fun compareTo(other: ULong): Int = this.number.compareTo(other.toLong())

    operator fun compareTo(other: UInt): Int = this.number.compareTo(other.toLong())
}
