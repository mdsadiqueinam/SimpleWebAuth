import kotlinx.serialization.Serializable
import models.AttestationConveyancePreference
import models.AuthenticatorSelectionCriteria
import models.COSEAlgorithmIdentifier
import models.ExcludeCredential
import models.supportedCOSEAlgorithmIdentifiers

@Serializable
@OptIn(ExperimentalUnsignedTypes::class)
data class GenerateRegistrationOptions(
    val rpName: String,
    val rpId: String,
    val userName: String,
    val userID: UByteArray,
    val challenge: UByteArray,
    val userDisplayName: String,
    val timeout: Long,
    val attestationType: AttestationConveyancePreference,
    val excludeCredentials: List<ExcludeCredential>,
    val authenticatorSelectionCriteria: AuthenticatorSelectionCriteria,
    val supportedAlgorithmIDs: List<COSEAlgorithmIdentifier>
) {
    init {
        require(supportedAlgorithmIDs.isNotEmpty()) { "supportedAlgorithmIDs cannot be empty" }
        check(supportedAlgorithmIDs.all { supportedCOSEAlgorithmIdentifiers.contains(it) }) { "supportedAlgorithmIDs must be from $supportedCOSEAlgorithmIdentifiers" }
    }
}