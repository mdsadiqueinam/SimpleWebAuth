import extensions.asBase64URLString
import extensions.asIsoUByteArray
import kotlinx.serialization.Serializable
import models.AttestationConveyancePreference
import models.AuthenticationExtensionsClientInputs
import models.AuthenticatorSelectionCriteria
import models.COSEAlgorithmIdentifier
import models.ExcludeCredential
import models.PublicKeyCredentialParameters
import models.defaultAuthenticatorSelection
import models.defaultSupportedAlgorithmIDs
import models.supportedCOSEAlgorithmIdentifiers
import kotlin.random.Random

@Serializable
@OptIn(ExperimentalUnsignedTypes::class)
data class GenerateRegistrationOptionsOpts(
    val rpName: String,
    val rpId: String,
    val userName: String,
    val userID: UByteArray,
    val challenge: UByteArray = Random.nextBytes(32).toUByteArray(),
    val userDisplayName: String = "",
    val timeout: Long = 60000,
    val attestationType: AttestationConveyancePreference = AttestationConveyancePreference.NONE,
    val excludeCredentials: List<ExcludeCredential> = emptyList(),
    val authenticatorSelection: AuthenticatorSelectionCriteria = defaultAuthenticatorSelection,
    val extensions: AuthenticationExtensionsClientInputs = AuthenticationExtensionsClientInputs(),
    val supportedAlgorithmIDs: List<COSEAlgorithmIdentifier> = defaultSupportedAlgorithmIDs
) {
    init {
        require(supportedAlgorithmIDs.isNotEmpty()) { "supportedAlgorithmIDs cannot be empty" }
        check(supportedAlgorithmIDs.all { it in supportedCOSEAlgorithmIdentifiers }) { "supportedAlgorithmIDs must be from $supportedCOSEAlgorithmIdentifiers" }
    }

    val pubKeyCredParams: List<PublicKeyCredentialParameters> by lazy {
        supportedAlgorithmIDs.map { PublicKeyCredentialParameters(alg = it) }
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
class GenerateRegistrationOptionsOptsBuilder {
    var rpName: String = ""

    var rpId: String = ""

    var userName: String = ""

    var userID: UByteArray = Random.nextBytes(32).toUByteArray()

    var base64URLUserID get(): String = userID.asBase64URLString()
        set(value) {
            userID = value.asIsoUByteArray()
        }

    var challenge: UByteArray = Random.nextBytes(32).toUByteArray()

    var base64URLChallenge get(): String = challenge.asBase64URLString()
        set(value) {
            challenge = value.asIsoUByteArray()
        }

    var userDisplayName: String = ""

    var timeout: Long = 60000

    var attestationType: AttestationConveyancePreference = AttestationConveyancePreference.NONE

    var excludeCredentials: List<ExcludeCredential> = emptyList()

    var authenticatorSelection: AuthenticatorSelectionCriteria = defaultAuthenticatorSelection

    var extensions: AuthenticationExtensionsClientInputs = AuthenticationExtensionsClientInputs()

    var supportedAlgorithmIDs: List<COSEAlgorithmIdentifier> = defaultSupportedAlgorithmIDs

    fun build(): GenerateRegistrationOptionsOpts {
        return GenerateRegistrationOptionsOpts(
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
}

fun generateRegistrationOptions(
    block: GenerateRegistrationOptionsOptsBuilder.() -> Unit
): GenerateRegistrationOptionsOpts {
    val options = GenerateRegistrationOptionsOptsBuilder().apply(block).build()
    return generateRegistrationOptions(options)
}

fun generateRegistrationOptions(options: GenerateRegistrationOptionsOpts): GenerateRegistrationOptionsOpts {}