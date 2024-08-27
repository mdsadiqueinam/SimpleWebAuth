package extensions

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun ByteArray.encodeToBase64URLString(): String {
    val encoded = Base64.UrlSafe.encode(this)
    return encoded.replace('+', '-').replace('/', '_').dropLastWhile { it == '=' }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun UByteArray.encodeToBase64URLString(): String {
    return asByteArray().encodeToBase64URLString()
}