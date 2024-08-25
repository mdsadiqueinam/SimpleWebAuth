package models

enum class UserVerificationRequirement(val value: String) {
    REQUIRED("required"),
    PREFERRED("preferred"),
    DISCOURAGED("discouraged"),
}