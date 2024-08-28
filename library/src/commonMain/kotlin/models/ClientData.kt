package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientData(
    val type: String,
    val challenge: String,
    val origin: String,
    val crossOrigin: Boolean? = null,
    val tokenBinding: TokenBinding? = null
)

@Serializable
data class TokenBinding(
    val id: String? = null,
    val status: Status,
) {
    @Serializable
    enum class Status {
        @SerialName("present")
        Present,
        @SerialName("supported")
        Supported,
        @SerialName("not-supported")
        NotSupported,
    }
}
