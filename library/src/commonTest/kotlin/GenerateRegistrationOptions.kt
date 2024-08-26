import extensions.encodeToBase64URLString
import models.AttestationConveyancePreference
import models.PublicKeyCredentialType
import models.ResidentKeyRequirement
import models.UserVerificationRequirement
import models.defaultSupportedAlgorithmIDs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GenerateRegistrationOptionsTest {
    @Test
    fun shouldGenerateCredentialRequestOptionsSuitableForSendingViaJSON() {
        val rpName = "SimpleWebAuth"
        val rpId = "not.real"
        val base64UserID = "1234567890"
        val userId = base64UserID.encodeToByteArray().encodeToBase64URLString()
        val userName = "usernameHere"
        val userDisplayName = "userDisplayName"
        val options = generateRegistrationOptions {
            this.rpName = rpName
            this.rpId = rpId
            base64Challenge = "totallyrandomvalue"
            this.base64UserID = base64UserID
            this.userName = userName
            timeout = 1
            attestationType = AttestationConveyancePreference.INDIRECT
            this.userDisplayName = userDisplayName
        }

        assertEquals(options.challenge, "dG90YWxseXJhbmRvbXZhbHVl")
        assertEquals(options.rp.id, rpId)
        assertEquals(options.rp.name, rpName)
        assertEquals(options.user.id, userId)
        assertEquals(options.user.name, userName)
        assertEquals(options.user.displayName, userDisplayName)
        assertEquals(options.timeout, 1)
        assertEquals(options.attestation, AttestationConveyancePreference.INDIRECT)
        assertTrue(defaultSupportedAlgorithmIDs.all { id -> options.pubKeyCredParams.find { it.alg == id && it.type == PublicKeyCredentialType } != null })
        assertEquals(options.pubKeyCredParams.size, defaultSupportedAlgorithmIDs.size)
        assertEquals(options.excludeCredentials.size, 0)
        assertEquals(options.authenticatorSelection.authenticatorAttachment, null)
        assertEquals(options.authenticatorSelection.requireResidentKey, false)
        assertEquals(options.authenticatorSelection.residentKey, ResidentKeyRequirement.PREFERRED)
        assertEquals(options.authenticatorSelection.userVerification, UserVerificationRequirement.PREFERRED)
        assertEquals(options.attestation, AttestationConveyancePreference.INDIRECT)
        assertEquals(options.extensions.credProps, true)
    }
}