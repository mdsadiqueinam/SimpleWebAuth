package models

import Base64URLString
import COSEAlgorithmIdentifier
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.properties.Delegates

@Serializable
enum class AuthenticatorAttachment {
    @SerialName("platform") PLATFORM,
    @SerialName("cross-platform") CROSS_PLATFORM,
}

@Serializable
enum class AuthenticatorTransport {
    @SerialName("usb") USB,
    @SerialName("ble") BLE,
    @SerialName("nfc") NFC,
    @SerialName("hybrid") HYBRID,
    @SerialName("internal") INTERNAL,
    @SerialName("smart-card") SMART_CARD,
    @SerialName("cable") CABLE,
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

//    val decodedClientDataJson get() {}
}