package models

data class AuthenticationExtensionsClientInputs(
    val appId: String? = null,
    val credProps: Boolean? = null,
    val hmacCreateSecret: Boolean? = null,
)

data class CredentialPropertiesOutput(
    val rk: Boolean? = null,
)

data class AuthenticationExtensionsClientOutputs(
    val appId: String? = null,
    val credProps: CredentialPropertiesOutput? = null,
    val hmacCreateSecret: Boolean? = null,
)