package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class UserVerificationRequirement(val value: String) {
    @SerialName("required") REQUIRED("required"),
    @SerialName("preferred") PREFERRED("preferred"),
    @SerialName("discouraged") DISCOURAGED("discouraged"),
}
