package it.mrschyzo.configuration

import it.mrschyzo.utils.types.PosLong
import it.mrschyzo.utils.types.toPosLong
import org.eclipse.microprofile.config.spi.Converter
import java.lang.IllegalArgumentException

class PositiveLongCoercionConverter : Converter<PosLong> {
    override fun convert(value: String?): PosLong =
        value?.toLong()?.toPosLong()?.getOrThrow() ?: throw IllegalArgumentException("Cannot coerce null value for PosLong")
}
