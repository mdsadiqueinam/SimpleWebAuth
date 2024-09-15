package models

import Base64URLString
import COSEAlgorithmIdentifier
import extensions.decodeToUTF8
import extensions.encodeToUByteArray
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.json.Json
import kotlin.properties.Delegates

@Serializable
enum class AuthenticatorAttachment(val value: String) {
    @SerialName("platform") PLATFORM("platform"),
    @SerialName("cross-platform") CROSS_PLATFORM("cross-platform"),
}

@Serializable
enum class AuthenticatorTransport(val value: String) {
    @SerialName("usb") USB("usb"),
    @SerialName("ble") BLE("ble"),
    @SerialName("nfc") NFC("nfc"),
    @SerialName("hybrid") HYBRID("hybrid"),
    @SerialName("internal") INTERNAL("internal"),
    @SerialName("smart-card") SMART_CARD("smart-card"),
    @SerialName("cable") CABLE("cable"),
}

@Serializable
data class AuthenticatorSelectionCriteria(
    val authenticatorAttachment: AuthenticatorAttachment? = null,
    val requireResidentKey: Boolean? = null,
    val residentKey: ResidentKeyRequirement? = null,
    val userVerification: UserVerificationRequirement? = null,
) {
    class Builder {
        var authenticatorAttachment: AuthenticatorAttachment? = null
        var requireResidentKey: Boolean? = null
        var residentKey: ResidentKeyRequirement? = null
        var userVerification: UserVerificationRequirement? = null

        fun authenticatorAttachment(authenticatorAttachment: AuthenticatorAttachment?) =
            apply { this.authenticatorAttachment = authenticatorAttachment }

        fun requireResidentKey(requireResidentKey: Boolean?) = apply { this.requireResidentKey = requireResidentKey }
        fun residentKey(residentKey: ResidentKeyRequirement?) = apply { this.residentKey = residentKey }
        fun userVerification(userVerification: UserVerificationRequirement?) =
            apply { this.userVerification = userVerification }

        fun build(): AuthenticatorSelectionCriteria {
            var _residentKey = residentKey
            var _requireResidentKey = requireResidentKey

            if (_residentKey == null) {
                if (_requireResidentKey == true) {
                    _residentKey = ResidentKeyRequirement.REQUIRED
                } else {
                    // _residentKey = ResidentKeyRequirement.DISCOURAGED;
                }
            } else {
                _requireResidentKey = _residentKey == ResidentKeyRequirement.REQUIRED
            }

            return AuthenticatorSelectionCriteria(
                authenticatorAttachment,
                requireResidentKey = _requireResidentKey,
                residentKey = _residentKey,
                userVerification,
            )
        }
    }

    companion object {
        fun builder() = Builder()
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
@Serializable
data class ParsedAuthenticatorData(
    val rpIdHash: UByteArray,
    val flagsBuf: UByteArray,
    val flags: Flags,
    val counter: Int,
    val counterBuf: UByteArray,
    val aaguid: UByteArray? = null,
    val credentialID: UByteArray? = null,
    val credentialPublicKey: UByteArray? = null,
    val extensionsData: AuthenticationExtensionsAuthenticatorOutputs? = null,
    val extensionsDataBuffer: UByteArray? = null
) {
    @Serializable
    data class Flags(
        val up: Boolean,
        val uv: Boolean,
        val be: Boolean,
        val bs: Boolean,
        val at: Boolean,
        val ed: Boolean,
        val flagsInt: Int
    )
}


@OptIn(ExperimentalUnsignedTypes::class, ExperimentalSerializationApi::class)
data class AuthenticatorAttestationResponse(
    val clientDataJSON: Base64URLString,
    val attestationObject: Base64URLString,
    val authenticatorData: Base64URLString,
    val transports: List<AuthenticatorTransport>,
    val publicKeyAlgorithm: COSEAlgorithmIdentifier,
    val publicKey: Base64URLString? = null
) {
    class Builder {
        lateinit var clientDataJSON: Base64URLString
        lateinit var attestationObject: Base64URLString
        lateinit var authenticatorData: Base64URLString
        lateinit var transports: List<AuthenticatorTransport>
        var publicKeyAlgorithm by Delegates.notNull<COSEAlgorithmIdentifier>()
        var publicKey: Base64URLString? = null

        fun clientDataJSON(clientDataJSON: Base64URLString) = apply { this.clientDataJSON = clientDataJSON }
        fun attestationObject(attestationObject: Base64URLString) = apply { this.attestationObject = attestationObject }
        fun authenticatorData(authenticatorData: Base64URLString) = apply { this.authenticatorData = authenticatorData }
        fun transports(transports: List<AuthenticatorTransport>) = apply { this.transports = transports }
        fun publicKeyAlgorithm(publicKeyAlgorithm: COSEAlgorithmIdentifier) =
            apply { this.publicKeyAlgorithm = publicKeyAlgorithm }

        fun publicKey(publicKey: Base64URLString?) = apply { this.publicKey = publicKey }

        fun build(): AuthenticatorAttestationResponse {
            return AuthenticatorAttestationResponse(
                clientDataJSON = this.clientDataJSON,
                attestationObject = this.attestationObject,
                authenticatorData = this.authenticatorData,
                transports = this.transports,
                publicKeyAlgorithm = this.publicKeyAlgorithm,
                publicKey = this.publicKey
            )
        }
    }

    companion object {
        fun builder() = Builder()
    }

    val decodedClientDataJson get(): ClientData {
        val decoded = clientDataJSON.decodeToUTF8()
        return Json.decodeFromString<ClientData>(decoded)
    }

    val decodedAttestationObject get(): AttestationObject {
        return Cbor.decodeFromByteArray(AttestationObject.serializer(), attestationObject.encodeToUByteArray().asByteArray())
    }
}