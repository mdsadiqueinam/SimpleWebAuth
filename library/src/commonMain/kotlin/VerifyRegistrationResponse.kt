import models.RegistrationResponse
import models.defaultSupportedAlgorithmIDs

data class VerifyRegistrationResponseOpts(
    val response: RegistrationResponse,
    val expectedChallenge: String,
    val expectedOrigin: List<String> = emptyList(),
    val expectedRPID: List<String> = emptyList(),
    val expectedType: List<String> = emptyList(),
    val requireUserVerification: Boolean = true,
    val supportedAlgorithmIDs: List<COSEAlgorithmIdentifier> = defaultSupportedAlgorithmIDs,
) {
    class Builder {
        lateinit var response: RegistrationResponse
        lateinit var expectedChallenge: String
        var expectedOrigin: List<String> = emptyList()
        var expectedRPID: List<String> = emptyList()
        var expectedType: List<String> = emptyList()
        var requireUserVerification: Boolean = true
        var supportedAlgorithmIDs: List<COSEAlgorithmIdentifier> = defaultSupportedAlgorithmIDs

        fun response(block: RegistrationResponse.Builder.() -> Unit) =
            apply { this.response = RegistrationResponse.builder().apply(block).build() }

        fun expectedChallenge(expectedChallenge: String) = apply { this.expectedChallenge = expectedChallenge }
        fun expectedOrigin(expectedOrigin: List<String>) = apply { this.expectedOrigin = expectedOrigin }
        fun expectedOrigin(expectedOrigin: String) = apply { this.expectedOrigin = listOf(expectedOrigin) }
        fun expectedRPID(expectedRPID: List<String>) = apply { this.expectedRPID = expectedRPID }
        fun expectedRPID(expectedRPID: String) = apply { this.expectedRPID = listOf(expectedRPID) }
        fun expectedType(expectedType: List<String>) = apply { this.expectedType = expectedType }
        fun expectedType(expectedType: String) = apply { this.expectedType = listOf(expectedType) }
        fun requireUserVerification(requireUserVerification: Boolean) =
            apply { this.requireUserVerification = requireUserVerification }

        fun supportedAlgorithmIDs(supportedAlgorithmIDs: List<COSEAlgorithmIdentifier>) =
            apply { this.supportedAlgorithmIDs = supportedAlgorithmIDs }

        fun build(): VerifyRegistrationResponseOpts {
            return VerifyRegistrationResponseOpts(
                response = this.response,
                expectedChallenge = this.expectedChallenge,
                expectedOrigin = this.expectedOrigin,
                expectedRPID = this.expectedRPID,
                expectedType = this.expectedType,
                requireUserVerification = this.requireUserVerification,
                supportedAlgorithmIDs = this.supportedAlgorithmIDs
            )
        }
    }
}

fun verifyRegistrationResponse(options: GenerateRegistrationOptionsOpts) {}