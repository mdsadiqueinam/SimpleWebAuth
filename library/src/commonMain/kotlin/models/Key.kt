package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class KeyFormat {
    @SerialName("jwk")
    JWK,

    @SerialName("pkcs8")
    PKCS8,

    @SerialName("raw")
    RAW,

    @SerialName("spki")
    SPKI,
}

@Serializable
enum class KeyType {
    @SerialName("private")
    PRIVATE,

    @SerialName("public")
    PUBLIC,

    @SerialName("secret")
    SECRET,
}

@Serializable
enum class KeyUsage {
    @SerialName("decrypt")
    DECRYPT,

    @SerialName("deriveBits")
    DERIVE_BITS,

    @SerialName("deriveKey")
    DERIVE_KEY,

    @SerialName("encrypt")
    ENCRYPT,

    @SerialName("sign")
    SIGN,

    @SerialName("unwrapKey")
    UNWRAP_KEY,

    @SerialName("verify")
    VERIFY,

    @SerialName("wrapKey")
    WRAP_KEY,
}