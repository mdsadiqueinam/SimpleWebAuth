package models

object PublicKeyCredentialType : CharSequence by "public-key" {}

data class PublicKeyCredentialParameters(
    val alg: COSEAlgorithmIdentifier,
    val type: PublicKeyCredentialType = PublicKeyCredentialType,
)