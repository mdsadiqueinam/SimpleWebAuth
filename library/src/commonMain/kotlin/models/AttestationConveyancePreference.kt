package models

enum class AttestationConveyancePreference(val value: String) {
    NONE("none"),
    DIRECT("direct"),
    INDIRECT("indirect"),
    ENTERPRISE("enterprise")
}