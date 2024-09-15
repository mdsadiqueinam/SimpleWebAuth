package models

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationExtensionsAuthenticatorOutputs(
    val devicePubKey: DevicePublicKeyAuthenticatorOutput? = null,
    val uvm: UVMAuthenticatorOutput? = null,
)


@OptIn(ExperimentalUnsignedTypes::class)
@Serializable
data class DevicePublicKeyAuthenticatorOutput(
    val dpk: UByteArray? = null,
    val sig: String? = null,
    val nonce: UByteArray? = null,
    val scope: UByteArray? = null,
    val aaguid: UByteArray? = null
)

@OptIn(ExperimentalUnsignedTypes::class)
@Serializable
data class UVMAuthenticatorOutput(
    val uvm: List<UByteArray>? = null
)