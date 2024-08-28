package extensions

import Base64URLString
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class, ExperimentalUnsignedTypes::class)
fun Base64URLString.encodeToUByteArray(): UByteArray {
    try {
        /**
         * Pad with '=' until it's a multiple of four
         * (4 - (85 % 4 = 1) = 3) % 4 = 3 padding
         * (4 - (86 % 4 = 2) = 2) % 4 = 2 padding
         * (4 - (87 % 4 = 3) = 1) % 4 = 1 padding
         * (4 - (88 % 4 = 0) = 4) % 4 = 0 padding
         */
        val padLength = (4 - (length % 4)) % 4
        val padded = padEnd(length + padLength, '=')
        return Base64.UrlSafe.decode(padded).asUByteArray()
    } catch (e: Exception) {
        return encodeToByteArray().asUByteArray()
    }
}

fun Base64URLString.encodeToUTF8(): String {
    return encodeToByteArray().decodeToString()
}