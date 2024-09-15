package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AttestationConveyancePreference(val value: String) {
    @SerialName("none") NONE("none"),
    @SerialName("direct") DIRECT("direct"),
    @SerialName("indirect") INDIRECT("indirect"),
    @SerialName("enterprise") ENTERPRISE("enterprise")
}