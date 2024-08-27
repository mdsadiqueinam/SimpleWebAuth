package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class UserVerificationRequirement {
    @SerialName("required")
    REQUIRED,

    @SerialName("preferred")
    PREFERRED,

    @SerialName("discouraged")
    DISCOURAGED,
}