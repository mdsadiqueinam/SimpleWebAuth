package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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