package it.mrschyzo.configuration

import it.mrschyzo.utils.types.PositiveLong
import it.mrschyzo.utils.types.toPosLong
import org.eclipse.microprofile.config.spi.Converter
import java.lang.IllegalArgumentException

class PositiveLongCoercionConverter : Converter<PositiveLong> {
    override fun convert(value: String?): PositiveLong =
        value?.toLong()?.toPosLong()?.getOrThrow() ?: throw IllegalArgumentException("Cannot coerce null value for PosLong")
}
