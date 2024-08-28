package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CredentialType {
    @SerialName("public-key") PUBLIC_KEY,
}

@Serializable
data class PublicKeyCredentialParameters(
    val alg: COSEAlgorithmIdentifier,
    val type: CredentialType = CredentialType.PUBLIC_KEY,
) {
    init {
        require(type == CredentialType.PUBLIC_KEY) { "type must be public-key" }
    }
}

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
    val type: CredentialType = CredentialType.PUBLIC_KEY,
    val transports: List<AuthenticatorTransport>
) {
    init {
        require(type == CredentialType.PUBLIC_KEY) { "type must be public-key" }
    }
}

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