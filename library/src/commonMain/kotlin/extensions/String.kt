package extensions

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class, ExperimentalUnsignedTypes::class)
fun String.asUByteArray(): UByteArray {
    try {
        // Convert from Base64URL to Base64
        val base64 = replace('-', '+').replace('_', '/')

        /**
         * Pad with '=' until it's a multiple of four
         * (4 - (85 % 4 = 1) = 3) % 4 = 3 padding
         * (4 - (86 % 4 = 2) = 2) % 4 = 2 padding
         * (4 - (87 % 4 = 3) = 1) % 4 = 1 padding
         * (4 - (88 % 4 = 0) = 4) % 4 = 0 padding
         */
        val padLength = (4 - (base64.length % 4)) % 4
        val padded = base64.padEnd(base64.length + padLength, '=')
        return Base64.UrlSafe.decode(padded).asUByteArray()
    } catch (e: Exception) {
        return encodeToByteArray().asUByteArray()
    }
}