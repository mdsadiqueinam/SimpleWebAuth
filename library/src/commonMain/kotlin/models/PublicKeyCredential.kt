package models

import kotlinx.serialization.Serializable

@Serializable
object PublicKeyCredentialType : CharSequence by "public-key" {}

@Serializable
data class PublicKeyCredentialParameters(
    val alg: COSEAlgorithmIdentifier,
    val type: PublicKeyCredentialType = PublicKeyCredentialType,
)

@Serializable
data class PublicKeyCredentialRpEntity(
    val id: Base64URLString,
    val name: String,
)

@Serializable
data class PublicKeyCredentialUserEntity(
    val id: Base64URLString,
    val name: String,
    val displayName: String,
)

@Serializable
data class PublicKeyCredentialDescriptor(
    val id: Base64URLString,
    val type: PublicKeyCredentialType = PublicKeyCredentialType,
    val transports: List<AuthenticatorTransport>
)

@Serializable
data class PublicKeyCredentialCreationOptions(
    val rp: PublicKeyCredentialRpEntity,
    val user: PublicKeyCredentialUserEntity,
    val challenge: Base64URLString,
    val pubKeyCredParams: List<PublicKeyCredentialParameters>,
    val timeout: Long,
    val excludeCredentials: List<PublicKeyCredentialDescriptor> = emptyList(),
    val authenticatorSelection: AuthenticatorSelectionCriteria,
    val attestation: AttestationConveyancePreference,
    val extensions: AuthenticationExtensionsClientInputs,
)