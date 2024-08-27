package models

val supportedCOSEAlgorithmIdentifiers: List<COSEAlgorithmIdentifier> = listOf(
    // EdDSA (In first position to encourage authenticators to use this over ES256)
    -8,
    // ECDSA w/ SHA-256
    -7,
    // ECDSA w/ SHA-512
    -36,
    // RSASSA-PSS w/ SHA-256
    -37,
    // RSASSA-PSS w/ SHA-384
    -38,
    // RSASSA-PSS w/ SHA-512
    -39,
    // RSASSA-PKCS1-v1_5 w/ SHA-256
    -257,
    // RSASSA-PKCS1-v1_5 w/ SHA-384
    -258,
    // RSASSA-PKCS1-v1_5 w/ SHA-512
    -259,
    // RSASSA-PKCS1-v1_5 w/ SHA-1 (Deprecated; here for legacy support)
    -65535,
)

val defaultAuthenticatorSelection = AuthenticatorSelectionCriteria.builder().apply {
    requireResidentKey = false
    residentKey = ResidentKeyRequirement.PREFERRED
    userVerification = UserVerificationRequirement.PREFERRED
}.build()

val defaultSupportedAlgorithmIDs: List<COSEAlgorithmIdentifier> = listOf(-8, -7, -257)