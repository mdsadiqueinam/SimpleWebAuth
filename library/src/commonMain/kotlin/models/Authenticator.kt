package models

import kotlinx.serialization.Serializable

@Serializable
enum class AuthenticatorAttachment(val value: String) {
    PLATFORM("platform"),
    CROSS_PLATFORM("cross-platform"),
}

@Serializable
enum class AuthenticatorTransport(val value: String) {
    USB("usb"),
    BLE("ble"),
    NFC("nfc"),
    HYBRID("hybrid"),
    INTERNAL("internal"),
    SMART_CARD("smart-card"),
    CABLE("cable"),
}

@Serializable
data class AuthenticatorSelectionCriteria(
    val authenticatorAttachment: AuthenticatorAttachment? = null,
    val requireResidentKey: Boolean? = null,
    val residentKey: ResidentKeyRequirement? = null,
    val userVerification: UserVerificationRequirement? = null,
)