package models

import Base64URLString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class VerifiedRegistrationResponse(
    val verified: Boolean,
    val registrationInfo: RegistrationInfo? = null,
)

@OptIn(ExperimentalUnsignedTypes::class)
data class RegistrationInfo(
    val fmt: AttestationFormat,
    val counter: Int,
    val aaguid: String,
    val credentialID: Base64URLString,
    val credentialPublicKey: UByteArray,
    val credentialType: CredentialType,
    val attestationObject: UByteArray,
    val userVerified: Boolean,
    val credentialDeviceType: CredentialDeviceType,
    val credentialBackedUp: Boolean,
    val origin: String,
    val rpID: String? = null,
    val authenticatorExtensionResults: AuthenticationExtensionsAuthenticatorOutputs? = null,
)

