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
class AuthenticatorSelectionCriteria private constructor(
    val authenticatorAttachment: AuthenticatorAttachment? = null,
    val requireResidentKey: Boolean? = null,
    val residentKey: ResidentKeyRequirement? = null,
    val userVerification: UserVerificationRequirement? = null,
) {
    companion object {
        fun create(
            authenticatorAttachment: AuthenticatorAttachment? = null,
            requireResidentKey: Boolean? = null,
            residentKey: ResidentKeyRequirement? = null,
            userVerification: UserVerificationRequirement? = null,
        ): AuthenticatorSelectionCriteria {
            var _residentKey = residentKey
            var _requireResidentKey = requireResidentKey

            /**
             * Capture some of the nuances of how `residentKey` and `requireResidentKey` how either is set
             * depending on when either is defined in the options
             */
            if (_residentKey == null) {

                /**
                 * `residentKey`: "If no value is given then the effective value is `required` if
                 * requireResidentKey is true or `discouraged` if it is false or absent."
                 *
                 * See https://www.w3.org/TR/webauthn-2/#dom-authenticatorselectioncriteria-residentkey
                 */
                if (_requireResidentKey == true) {
                    _residentKey = ResidentKeyRequirement.REQUIRED
                } else {
                    /**
                     * FIDO Conformance v1.7.2 fails the first test if we do this, even though this is
                     * technically compatible with the WebAuthn L2 spec...
                     */
//                     _residentKey = ResidentKeyRequirement.DISCOURAGED;
                }
            } else {
                /**
                 * `requireResidentKey`: "Relying Parties SHOULD set it to true if, and only if, residentKey is
                 * set to "required""
                 *
                 * Spec says this property defaults to `false` so we should still be okay to assign `false` too
                 *
                 * See https://www.w3.org/TR/webauthn-2/#dom-authenticatorselectioncriteria-requireresidentkey
                 */
                _requireResidentKey = _residentKey == ResidentKeyRequirement.REQUIRED;
            }

            return AuthenticatorSelectionCriteria(
                authenticatorAttachment,
                requireResidentKey = _requireResidentKey,
                residentKey = _residentKey,
                userVerification,
            )
        }
    }
}