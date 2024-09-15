package cbor

import kotlinx.serialization.Serializable


@OptIn(ExperimentalUnsignedTypes::class)
@Serializable
sealed class CBORType {
    @Serializable
    data class Number(val value: Double) : CBORType()

    @Serializable
    data class BigInt(val value: Long) : CBORType()

    @Serializable
    data class Str(val value: String) : CBORType()

    @Serializable
    data class ByteArrayVal(val value: UByteArray) : CBORType()

    @Serializable
    data class Bool(val value: Boolean) : CBORType()

    @Serializable
    data class ArrayType(val value: List<CBORType>) : CBORType()

    @Serializable
    data class Tag(val tag: CBORTag) : CBORType()

    @Serializable
    data class MapType(val value: Map<String, CBORType>) : CBORType()
}

@Serializable
data class CBORTag(val tagId: Int, val tagValue: CBORType)


fun Map<String, CBORType>.get(key: Int): CBORType? {
    return get(key.toString())
}

fun Map<String, CBORType>.get(key: Float): CBORType? {
    return get(key.toString())
}

fun MutableMap<String, CBORType>.put(key: Int, value: CBORType): CBORType? {
    return put(key.toString(), value)
}

fun MutableMap<String, CBORType>.put(key: Float, value: CBORType): CBORType? {
    return put(key.toString(), value)
}