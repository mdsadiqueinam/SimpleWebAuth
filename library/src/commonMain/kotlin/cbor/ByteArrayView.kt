package cbor

@OptIn(ExperimentalUnsignedTypes::class)
class ByteArrayView(private val byteArray: UByteArray) {

    /**
     * Get an unsigned 8-bit integer (UByte) from the byte array at a specific index.
     */
    fun getUint8(index: Int): UByte {
        return byteArray[index]
    }

    /**
     * Get a signed 8-bit integer (Byte) from the byte array at a specific index.
     */
    fun getInt8(index: Int): Byte {
        return byteArray[index].toByte()
    }

    /**
     * Get a signed 16-bit integer (Short) from the byte array at a specific index (big-endian).
     */
    fun getInt16(index: Int, littleEndian: Boolean = false): Short {
        val high = byteArray[index].toInt()
        val low = byteArray[index + 1].toInt()
        return if (littleEndian) {
            ((low shl 8) or high).toShort()
        } else {
            ((high shl 8) or low).toShort()
        }
    }

    /**
     * Get an unsigned 16-bit integer (UShort) from the byte array at a specific index (big-endian).
     */
    fun getUint16(index: Int, littleEndian: Boolean = false): UShort {
        val high = byteArray[index].toInt()
        val low = byteArray[index + 1].toInt()
        return if (littleEndian) {
            ((low shl 8) or high).toUShort()
        } else {
            ((high shl 8) or low).toUShort()
        }
    }

    /**
     * Get a 32-bit float from the byte array at a specific index (big-endian).
     */
    fun getFloat32(index: Int, littleEndian: Boolean = false): Float {
        val intBits = getInt32(index, littleEndian)
        return Float.fromBits(intBits)
    }

    /**
     * Get a signed 32-bit integer (Int) from the byte array at a specific index (big-endian).
     */
    fun getInt32(index: Int, littleEndian: Boolean = false): Int {
        val byte1 = byteArray[index].toInt()
        val byte2 = byteArray[index + 1].toInt()
        val byte3 = byteArray[index + 2].toInt()
        val byte4 = byteArray[index + 3].toInt()

        return if (littleEndian) {
            (byte4 shl 24) or (byte3 shl 16) or (byte2 shl 8) or byte1
        } else {
            (byte1 shl 24) or (byte2 shl 16) or (byte3 shl 8) or byte4
        }
    }

    /**
     * Set an unsigned 8-bit integer (UByte) at a specific index.
     */
    fun setUint8(index: Int, value: UByte) {
        byteArray[index] = value
    }

    /**
     * Set a signed 8-bit integer (Byte) at a specific index.
     */
    fun setInt8(index: Int, value: Byte) {
        byteArray[index] = value.toUByte()
    }

    /**
     * Set a signed 16-bit integer (Short) at a specific index (big-endian).
     */
    fun setInt16(index: Int, value: Short, littleEndian: Boolean = false) {
        val high = (value.toInt() shr 8).toUByte()
        val low = (value.toInt() and 0xFF).toUByte()

        if (littleEndian) {
            byteArray[index] = low
            byteArray[index + 1] = high
        } else {
            byteArray[index] = high
            byteArray[index + 1] = low
        }
    }

    /**
     * Set an unsigned 16-bit integer (UShort) at a specific index (big-endian).
     */
    fun setUint16(index: Int, value: UShort, littleEndian: Boolean = false) {
        val high = (value.toInt() shr 8).toUByte()
        val low = (value.toInt() and 0xFF).toUByte()

        if (littleEndian) {
            byteArray[index] = low
            byteArray[index + 1] = high
        } else {
            byteArray[index] = high
            byteArray[index + 1] = low
        }
    }

    /**
     * Set a signed 32-bit integer (Int) at a specific index (big-endian).
     */
    fun setInt32(index: Int, value: Int, littleEndian: Boolean = false) {
        val byte1 = ((value shr 24) and 0xFF).toUByte()
        val byte2 = ((value shr 16) and 0xFF).toUByte()
        val byte3 = ((value shr 8) and 0xFF).toUByte()
        val byte4 = (value and 0xFF).toUByte()

        if (littleEndian) {
            byteArray[index] = byte4
            byteArray[index + 1] = byte3
            byteArray[index + 2] = byte2
            byteArray[index + 3] = byte1
        } else {
            byteArray[index] = byte1
            byteArray[index + 1] = byte2
            byteArray[index + 2] = byte3
            byteArray[index + 3] = byte4
        }
    }
}
