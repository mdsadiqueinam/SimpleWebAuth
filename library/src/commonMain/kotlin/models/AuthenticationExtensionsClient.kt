package models

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationExtensionsClientInputs(
    val appId: String? = null,
    val credProps: Boolean? = null,
    val hmacCreateSecret: Boolean? = null,
)

@Serializable
data class CredentialPropertiesOutput(
    val rk: Boolean? = null,
)

@Serializable
data class AuthenticationExtensionsClientOutputs(
    val appId: String? = null,
    val credProps: CredentialPropertiesOutput? = null,
    val hmacCreateSecret: Boolean? = null,
)