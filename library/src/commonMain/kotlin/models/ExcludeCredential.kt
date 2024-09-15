package models

import Base64URLString
import kotlinx.serialization.Serializable

@Serializable
data class ExcludeCredential(
    val id: Base64URLString,
    val transports: List<AuthenticatorTransport>
)
