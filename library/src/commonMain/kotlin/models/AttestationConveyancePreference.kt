package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AttestationConveyancePreference {
    @SerialName("none") NONE,
    @SerialName("direct") DIRECT,
    @SerialName("indirect") INDIRECT,
    @SerialName("enterprise") ENTERPRISE
}