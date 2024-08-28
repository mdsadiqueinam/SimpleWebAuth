package models

data class RegistrationResponse(
    val id: String,
    val rawId: Base64URLString,
    val response: AuthenticatorAttestationResponse,
    val authenticatorAttachment: AuthenticatorAttachment? = null,
    val clientExtensionResults: AuthenticationExtensionsClientOutputs,
    val type: CredentialType = CredentialType.PUBLIC_KEY
) {
    init {
        require(id != rawId) { "Credential ID was not base64url-encoded" }
        require(type == CredentialType.PUBLIC_KEY) { "Credential type was not public-key" }
    }

    class Builder {
        lateinit var id: String
        lateinit var rawId: Base64URLString
        lateinit var response: AuthenticatorAttestationResponse
        var authenticatorAttachment: AuthenticatorAttachment? = null
        lateinit var clientExtensionResults: AuthenticationExtensionsClientOutputs

        fun id(id: String) = apply { this.id = id }
        fun rawId(rawId: Base64URLString) = apply { this.rawId = rawId }
        fun response(block: AuthenticatorAttestationResponse.Builder.() -> Unit) =
            apply { this.response = AuthenticatorAttestationResponse.builder().apply(block).build() }

        fun authenticatorAttachment(authenticatorAttachment: AuthenticatorAttachment?) =
            apply { this.authenticatorAttachment = authenticatorAttachment }

        fun clientExtensionResults(clientExtensionResults: AuthenticationExtensionsClientOutputs) =
            apply { this.clientExtensionResults = clientExtensionResults }

        fun build(): RegistrationResponse {
            return RegistrationResponse(
                id = this.id,
                rawId = this.rawId,
                response = this.response,
                authenticatorAttachment = this.authenticatorAttachment,
                clientExtensionResults = this.clientExtensionResults,
            )
        }
    }

    companion object {
        fun builder() = Builder()
    }
}