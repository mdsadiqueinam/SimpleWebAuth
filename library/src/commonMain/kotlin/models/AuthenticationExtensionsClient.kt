package models

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationExtensionsClientInputs(
    val appId: String? = null,
    val credProps: Boolean? = null,
    val hmacCreateSecret: Boolean? = null,
) {
    class Builder {
        var appId: String? = null
        var credProps: Boolean? = null
        var hmacCreateSecret: Boolean? = null

        fun appId(appId: String?) = apply { this.appId = appId }
        fun credProps(credProps: Boolean?) = apply { this.credProps = credProps }
        fun hmacCreateSecret(hmacCreateSecret: Boolean?) = apply { this.hmacCreateSecret = hmacCreateSecret }

        fun build(): AuthenticationExtensionsClientInputs {
            return AuthenticationExtensionsClientInputs(
                appId = appId,
                credProps = credProps,
                hmacCreateSecret = hmacCreateSecret,
            )
        }
    }

    companion object {
        fun builder() = Builder()
    }
}

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