package models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray

@Serializable
enum class AttestationFormat(val format: String) {
    @SerialName("fido-u2f")
    FIDO_U2F("fido-u2f"), @SerialName("packed")
    PACKED("packed"), @SerialName("android-safetynet")
    ANDROID_SAFETYNET("android-safetynet"), @SerialName("android-key")
    ANDROID_KEY("android-key"), @SerialName("tpm")
    TPM("tpm"), @SerialName("apple")
    APPLE("apple"), @SerialName("none")
    NONE("none");
}

@OptIn(ExperimentalUnsignedTypes::class)
@Serializable
data class AttestationObject(
    val fmt: AttestationFormat, val attStmt: AttestationStatement, val authData: UByteArray
)

@OptIn(ExperimentalUnsignedTypes::class)
@Serializable
data class AttestationStatement(
    val sig: UByteArray? = null,
    val x5c: List<UByteArray>? = null,
    val response: UByteArray? = null,
    val alg: Int? = null,
    val ver: String? = null,
    val certInfo: UByteArray? = null,
    val pubArea: UByteArray? = null,
) {
    val isEmpty
        get(): Boolean {
            return sig == null && x5c == null && response == null && alg == null && ver == null && certInfo == null && pubArea == null
        }
}

@OptIn(ExperimentalSerializationApi::class)
fun decodeAttestationObject(byteArray: ByteArray, index: Int = 0) {
    val decoded = Cbor.decodeFromByteArray<HashMap<String, Any>>(byteArray)
    println(decoded)
}