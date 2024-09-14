package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class KeyFormat(val value: String) {
    @SerialName("jwk") JWK("jwk"),
    @SerialName("pkcs8") PKCS8("pkcs8"),
    @SerialName("raw") RAW("raw"),
    @SerialName("spki") SPKI("spki"),
}

@Serializable
enum class KeyType(val value: String) {
    @SerialName("private") PRIVATE("private"),
    @SerialName("public") PUBLIC("public"),
    @SerialName("secret") SECRET("secret"),
}

@Serializable
enum class KeyUsage(val value: String) {
    @SerialName("decrypt") DECRYPT("decrypt"),
    @SerialName("deriveBits") DERIVE_BITS("deriveBits"),
    @SerialName("deriveKey") DERIVE_KEY("deriveKey"),
    @SerialName("encrypt") ENCRYPT("encrypt"),
    @SerialName("sign") SIGN("sign"),
    @SerialName("unwrapKey") UNWRAP_KEY("unwrapKey"),
    @SerialName("verify") VERIFY("verify"),
    @SerialName("wrapKey") WRAP_KEY("wrapKey"),
}
