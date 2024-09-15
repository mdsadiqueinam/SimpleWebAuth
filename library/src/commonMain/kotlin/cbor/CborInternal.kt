package cbor

object MajorType {
    val UNSIGNED_INTEGER = 0
    val NEGATIVE_INTEGER = 1
    val BYTE_STRING = 2
    val TEXT_STRING = 3
    val ARRAY = 4
    val MAP = 5
    val TAG = 6
    val SIMPLE_OR_FLOAT = 7
}

@OptIn(ExperimentalUnsignedTypes::class)
inline fun decodeLength(
    data: UByteArray, argument: Int, index: Int
): Pair<UInt, UByte> {
    if (argument < 24) {
        return Pair(argument.toUInt(), 1u)
    }

    val remainingDataLength = data.size - index - 1
    val byteArray = data.sliceArray(index + 1 until data.size)

    var output: UInt? = null
    var bytes: UByte = 0u

    when (argument) {
        24 -> if (remainingDataLength > 0) {
            output = byteArray[0].toUInt()
            bytes = 2u
        }
        25 -> if (remainingDataLength > 1) {
            output = byteArray[0].toUInt()
            bytes = 3u
        }
        26 -> if (remainingDataLength > 3) {
            output = byteArray[0].toUInt()
            bytes = 5u;
        }
        27 -> if (remainingDataLength > 7) {
            val bigOutput = byteArray[0].toULong()
            if (bigOutput >= 24u && bigOutput <= UInt.MAX_VALUE) {
                output = bigOutput.toUInt()
                bytes = 9u
            }
        }
    }

    println(output)
    if (output != null && output >= 24u) {
        return Pair(output, bytes)
    }

    throw Exception("Length not supported or not well formed")
}
