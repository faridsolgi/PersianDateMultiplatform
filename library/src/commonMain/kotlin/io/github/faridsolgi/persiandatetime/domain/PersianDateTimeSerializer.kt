package io.github.faridsolgi.persiandatetime.domain

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object PersianDateTimeSerializer : KSerializer<PersianDateTime> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("PersianDateTime") {
            element<Int>("year")
            element<Int>("month")
            element<Int>("day")
            element<Int>("hour")
            element<Int>("minute")
            element<Int>("second")
        }

    override fun serialize(encoder: Encoder, value: PersianDateTime) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeIntElement(descriptor, 0, value.year)
        composite.encodeIntElement(descriptor, 1, value.month)
        composite.encodeIntElement(descriptor, 2, value.day)
        composite.encodeIntElement(descriptor, 3, value.hour)
        composite.encodeIntElement(descriptor, 4, value.minute)
        composite.encodeIntElement(descriptor, 5, value.second)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): PersianDateTime {
        val str = decoder.decodeString()
        return PersianDateTime.parse(str)
    }
}