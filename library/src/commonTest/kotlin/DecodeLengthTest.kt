import cbor.decodeLength
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalUnsignedTypes::class)
class DecodeLengthTest {

    @Test
    fun testDecodeLengthWithUByteArray() {
//        assertEquals<Pair<UInt, UByte>>(decodeLength(ubyteArrayOf(0u), 0, 0), Pair(0u, 1u))
//        assertEquals<Pair<UInt, UByte>>(decodeLength(ubyteArrayOf(10u), 10, 0), Pair(10u, 1u))
//        assertEquals<Pair<UInt, UByte>>(decodeLength(ubyteArrayOf(24u, 24u), 24, 0), Pair(24u, 2u))
//        assertEquals<Pair<UInt, UByte>>(decodeLength(ubyteArrayOf(24u, 25u), 24, 0), Pair(25u, 2u))
        assertEquals<Pair<UInt, UByte>>(decodeLength(ubyteArrayOf(25u, 16u, 0u), 25, 0), Pair(4096u, 3u))
    }

}
