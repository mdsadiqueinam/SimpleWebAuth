package models

enum class ResidentKeyRequirement(val value: String) {
    REQUIRED("required"),
    PREFERRED("preferred"),
    DISCOURAGED("discouraged"),
}