package models

enum class KeyFormat(val value: String) {
    JWK("jwk"),
    PKCS8("pkcs8"),
    RAW("raw"),
    SPKI("spki"),
}

enum class KeyType(val value: String) {
    PRIVATE("private"),
    PUBLIC("public"),
    SECRET("secret"),
}

enum class KeyUsage(val value: String) {
    DECRYPT("decrypt"),
    DERIVE_BITS("deriveBits"),
    DERIVE_KEY("deriveKey"),
    ENCRYPT("encrypt"),
    SIGN("sign"),
    UNWRAP_KEY("unwrapKey"),
    VERIFY("verify"),
    WRAP_KEY("wrapKey"),
}