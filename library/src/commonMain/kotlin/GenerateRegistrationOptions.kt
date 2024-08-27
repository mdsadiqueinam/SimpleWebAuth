@file:OptIn(ExperimentalUnsignedTypes::class)

import extensions.encodeToBase64URLString
import extensions.encodeToUByteArray
import kotlinx.serialization.Serializable
import models.AttestationConveyancePreference
import models.AuthenticationExtensionsClientInputs
import models.AuthenticatorSelectionCriteria
import models.COSEAlgorithmIdentifier
import models.ExcludeCredential
import models.PublicKeyCredentialCreationOptions
import models.PublicKeyCredentialDescriptor
import models.PublicKeyCredentialParameters
import models.PublicKeyCredentialRpEntity
import models.PublicKeyCredentialUserEntity
import models.defaultAuthenticatorSelection
import models.defaultSupportedAlgorithmIDs
import models.supportedCOSEAlgorithmIdentifiers
import kotlin.random.Random

@Serializable
data class GenerateRegistrationOptionsOpts(
    val rpName: String,
    val rpId: String,
    val userName: String,
    val userID: UByteArray = Random.nextBytes(32).toUByteArray(),
    val challenge: UByteArray = Random.nextBytes(32).toUByteArray(),
    val userDisplayName: String = "",
    val timeout: Long = 60000,
    val attestationType: AttestationConveyancePreference = AttestationConveyancePreference.NONE,
    val excludeCredentials: List<ExcludeCredential> = emptyList(),
    val authenticatorSelection: AuthenticatorSelectionCriteria = defaultAuthenticatorSelection,
    val extensions: AuthenticationExtensionsClientInputs = AuthenticationExtensionsClientInputs(),
    val supportedAlgorithmIDs: List<COSEAlgorithmIdentifier> = defaultSupportedAlgorithmIDs
) {

    @OptIn(ExperimentalUnsignedTypes::class)
    class Builder {
        var rpName: String = ""
        var rpId: String = ""
        var userName: String = ""
        var userID: UByteArray = Random.nextBytes(32).toUByteArray()
        var base64UserID: String
            get() = userID.encodeToBase64URLString()
            set(value) {
                userID = value.encodeToUByteArray()
            }
        var challenge: UByteArray = Random.nextBytes(32).toUByteArray()
        var base64Challenge: String
            get() = challenge.encodeToBase64URLString()
            set(value) {
                challenge = value.encodeToUByteArray()
            }
        var userDisplayName: String = ""
        var timeout: Long = 60000
        var attestationType: AttestationConveyancePreference = AttestationConveyancePreference.NONE
        var excludeCredentials: List<ExcludeCredential> = emptyList()
        var authenticatorSelection: AuthenticatorSelectionCriteria = defaultAuthenticatorSelection
        var extensions: AuthenticationExtensionsClientInputs = AuthenticationExtensionsClientInputs()
        var supportedAlgorithmIDs: List<COSEAlgorithmIdentifier> = defaultSupportedAlgorithmIDs

        fun authenticatorSelection(block: AuthenticatorSelectionCriteria.Builder.() -> Unit) =
            apply { authenticatorSelection = AuthenticatorSelectionCriteria.builder().apply(block).build() }

        fun build() = GenerateRegistrationOptionsOpts(
            rpName = rpName,
            rpId = rpId,
            userName = userName,
            userID = userID,
            challenge = challenge,
            userDisplayName = userDisplayName,
            timeout = timeout,
            attestationType = attestationType,
            excludeCredentials = excludeCredentials,
            authenticatorSelection = authenticatorSelection,
            extensions = extensions,
            supportedAlgorithmIDs = supportedAlgorithmIDs,
        )
    }

    init {
        require(supportedAlgorithmIDs.isNotEmpty()) { "supportedAlgorithmIDs cannot be empty" }
        check(supportedAlgorithmIDs.all { it in supportedCOSEAlgorithmIdentifiers }) { "supportedAlgorithmIDs must be from $supportedCOSEAlgorithmIdentifiers" }
    }

    val pubKeyCredParams: List<PublicKeyCredentialParameters> by lazy {
        supportedAlgorithmIDs.map { PublicKeyCredentialParameters(alg = it) }
    }
}

inline fun generateRegistrationOptions(
    block: GenerateRegistrationOptionsOpts.Builder.() -> Unit
): PublicKeyCredentialCreationOptions {
    val options = GenerateRegistrationOptionsOpts.Builder().apply(block).build()
    return generateRegistrationOptions(options)
}

fun generateRegistrationOptions(options: GenerateRegistrationOptionsOpts): PublicKeyCredentialCreationOptions {
    return PublicKeyCredentialCreationOptions(
        challenge = options.challenge.encodeToBase64URLString(),
        rp = PublicKeyCredentialRpEntity(
            id = options.rpId,
            name = options.rpName,
        ),
        user = PublicKeyCredentialUserEntity(
            id = options.userID.encodeToBase64URLString(),
            name = options.userName,
            displayName = options.userDisplayName,
        ),
        pubKeyCredParams = options.pubKeyCredParams,
        timeout = options.timeout,
        attestation = options.attestationType,
        excludeCredentials = options.excludeCredentials.map {
            PublicKeyCredentialDescriptor(
                id = it.id,
                transports = it.transports
            )
        },
        extensions = options.extensions.copy(credProps = true),
        authenticatorSelection = options.authenticatorSelection,
    )
}