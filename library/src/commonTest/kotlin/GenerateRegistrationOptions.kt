import extensions.encodeToBase64URLString
import models.AttestationConveyancePreference
import models.AuthenticatorAttachment
import models.AuthenticatorTransport
import models.ExcludeCredential
import models.PublicKeyCredentialDescriptor
import models.PublicKeyCredentialParameters
import models.PublicKeyCredentialType
import models.ResidentKeyRequirement
import models.UserVerificationRequirement
import models.defaultSupportedAlgorithmIDs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class GenerateRegistrationOptionsTest {
    private val rpName = "SimpleWebAuth"
    private val rpId = "not.real"
    private val base64UserID = "1234567890"
    private val userId = base64UserID.encodeToByteArray().encodeToBase64URLString()
    private val userName = "usernameHere"
    private val userDisplayName = "userDisplayName"
    private val challenge = "totallyrandomvalue"

    @Test
    fun shouldGenerateCredentialRequestOptionsSuitableForSendingViaJSON() {
        val options = generateRegistrationOptions {
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            base64Challenge = this@GenerateRegistrationOptionsTest.challenge
            this.base64UserID = this@GenerateRegistrationOptionsTest.base64UserID
            this.userName = this@GenerateRegistrationOptionsTest.userName
            timeout = 1
            attestationType = AttestationConveyancePreference.INDIRECT
            this.userDisplayName = this@GenerateRegistrationOptionsTest.userDisplayName
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

    @Test
    fun shouldMapExcludedCredentialIDsIfSpecified() {
        val transports = listOf(
            AuthenticatorTransport.USB,
            AuthenticatorTransport.NFC,
            AuthenticatorTransport.BLE,
            AuthenticatorTransport.HYBRID
        )
        val options = generateRegistrationOptions {
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            base64Challenge = this@GenerateRegistrationOptionsTest.challenge
            this.base64UserID = this@GenerateRegistrationOptionsTest.base64UserID
            this.userName = this@GenerateRegistrationOptionsTest.userName
            excludeCredentials = listOf(
                ExcludeCredential(
                    id = "somewhereOverTheRainbow",
                    transports = transports
                )
            )
        }

        val publicKeyCredentialDescriptor = PublicKeyCredentialDescriptor(
            id = "somewhereOverTheRainbow",
            type = PublicKeyCredentialType,
            transports = transports
        )

        assertEquals(options.excludeCredentials.size, 1)
        assertEquals(options.excludeCredentials[0], publicKeyCredentialDescriptor)
    }

    @Test
    fun defaultsTo60SecondsIfNoTimeoutIsSpecified() {
        val options = generateRegistrationOptions {}
        assertEquals(options.timeout, 60000)
    }

    @Test
    fun defaultsToNoneAttestationIfNoAttestationTypeIsSpecified() {
        val options = generateRegistrationOptions {
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            base64Challenge = this@GenerateRegistrationOptionsTest.challenge
            this.userName = this@GenerateRegistrationOptionsTest.userName
        }

        assertEquals(options.attestation, AttestationConveyancePreference.NONE)
    }

    @Test
    fun defaultsToEmptyStringForDisplayNameIfNoUserDisplayNameIsSpecified() {
        val options = generateRegistrationOptions {
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            base64Challenge = this@GenerateRegistrationOptionsTest.challenge
            this.userName = this@GenerateRegistrationOptionsTest.userName
        }

        assertEquals(options.user.displayName, "")
    }

    @Test
    fun shouldSetAuthenticatorSelectionIfSpecified() {
        val options = generateRegistrationOptions {
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            base64Challenge = this@GenerateRegistrationOptionsTest.challenge
            this.userName = this@GenerateRegistrationOptionsTest.userName
            authenticatorSelection {
                authenticatorAttachment = AuthenticatorAttachment.CROSS_PLATFORM
                requireResidentKey = false
                userVerification = UserVerificationRequirement.PREFERRED
            }
        }

        assertEquals(options.authenticatorSelection.authenticatorAttachment, AuthenticatorAttachment.CROSS_PLATFORM)
        assertEquals(options.authenticatorSelection.requireResidentKey, false)
        assertEquals(options.authenticatorSelection.userVerification, UserVerificationRequirement.PREFERRED)
    }

    @Test
    fun shouldSetExtensionsIfSpecified() {
        val options = generateRegistrationOptions {
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            base64Challenge = this@GenerateRegistrationOptionsTest.challenge
            this.userName = this@GenerateRegistrationOptionsTest.userName
            extensions {
                appId = "simplewebauthn"
            }
        }

        assertEquals(options.extensions.appId, "simplewebauthn")
    }

    @Test
    fun shouldIncludeCredPropsIfExtensionsAreNotProvided() {
        val options = generateRegistrationOptions {
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            this.userName = this@GenerateRegistrationOptionsTest.userName
        }

        assertEquals(options.extensions.credProps, true)
    }

    @Test
    fun shouldIncludeCredPropsIfExtensionsAreProvided() {
        val options = generateRegistrationOptions {
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            this.userName = this@GenerateRegistrationOptionsTest.userName
            extensions {
                appId = "simplewebauthn"
            }
        }

        assertEquals(options.extensions.credProps, true)
    }

    @Test
    fun shouldGenerateChallengeIfOneIsNotProvided() {
        val options = generateRegistrationOptions {
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.userName = this@GenerateRegistrationOptionsTest.userName
        }

        assertTrue(options.challenge.isNotEmpty())
    }

    @Test
    fun shouldTreatStringChallengesAsUTF8Strings() {
        val options = generateRegistrationOptions {
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.userName = this@GenerateRegistrationOptionsTest.userName
            base64Challenge = "こんにちは"
        }

        assertEquals(options.challenge, "44GT44KT44Gr44Gh44Gv")
    }

    @Test
    fun shouldUseCustomSupportedAlgorithmIDsAsIsWhenProvided() {
        val customSupportedAlgorithmIDs = listOf(-7, -8, -65535)
        val options = generateRegistrationOptions {
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.userName = this@GenerateRegistrationOptionsTest.userName
            supportedAlgorithmIDs = customSupportedAlgorithmIDs
        }

        val expectedParams = customSupportedAlgorithmIDs.map { algId ->
            PublicKeyCredentialParameters(type = PublicKeyCredentialType, alg = algId)
        }

        assertEquals(options.pubKeyCredParams, expectedParams)
    }

    @Test
    fun shouldRequireResidentKeyIfResidentKeyOptionIsAbsentButRequireResidentKeyIsSetToTrue() {
        val options = generateRegistrationOptions {
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.userName = this@GenerateRegistrationOptionsTest.userName
            authenticatorSelection {
                requireResidentKey = true
            }
        }

        assertEquals(options.authenticatorSelection.requireResidentKey, true)
        assertEquals(options.authenticatorSelection.residentKey, ResidentKeyRequirement.REQUIRED)
    }

    @Test
    fun shouldDiscourageResidentKeyIfResidentKeyOptionIsAbsentButRequireResidentKeyIsSetToFalse() {
        val options = generateRegistrationOptions {
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.userName = this@GenerateRegistrationOptionsTest.userName
            authenticatorSelection {
                requireResidentKey = false
            }
        }

        assertEquals(options.authenticatorSelection.requireResidentKey, false)
        assertEquals(options.authenticatorSelection.residentKey, null)
    }

    @Test
    fun shouldPreferResidentKeyIfBothResidentKeyAndRequireResidentKeyOptionsAreAbsent() {
        val options = generateRegistrationOptions {
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.userName = this@GenerateRegistrationOptionsTest.userName
        }

        assertEquals(options.authenticatorSelection.requireResidentKey, false)
        assertEquals(options.authenticatorSelection.residentKey, ResidentKeyRequirement.PREFERRED)
    }

    @Test
    fun shouldSetRequireResidentKeyToTrueIfResidentKeyIsSetToRequired() {
        val options = generateRegistrationOptions {
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.userName = this@GenerateRegistrationOptionsTest.userName
            authenticatorSelection {
                residentKey = ResidentKeyRequirement.REQUIRED
            }
        }

        assertEquals(options.authenticatorSelection.requireResidentKey, true)
        assertEquals(options.authenticatorSelection.residentKey, ResidentKeyRequirement.REQUIRED)
    }

    @Test
    fun shouldSetRequireResidentKeyToFalseIfResidentKeyIsSetToPreferred() {
        val options = generateRegistrationOptions {
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.userName = this@GenerateRegistrationOptionsTest.userName
            authenticatorSelection {
                residentKey = ResidentKeyRequirement.PREFERRED
            }
        }

        assertEquals(options.authenticatorSelection.requireResidentKey, false)
        assertEquals(options.authenticatorSelection.residentKey, ResidentKeyRequirement.PREFERRED)
    }

    @Test
    fun shouldSetRequireResidentKeyToFalseIfResidentKeyIsSetToDiscouraged() {
        val options = generateRegistrationOptions {
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.userName = this@GenerateRegistrationOptionsTest.userName
            authenticatorSelection {
                residentKey = ResidentKeyRequirement.DISCOURAGED
            }
        }

        assertEquals(options.authenticatorSelection.requireResidentKey, false)
        assertEquals(options.authenticatorSelection.residentKey, ResidentKeyRequirement.DISCOURAGED)
    }

    @Test
    fun shouldPreferEd25519InPubKeyCredParams() {
        val options = generateRegistrationOptions {
            this.rpName = this@GenerateRegistrationOptionsTest.rpName
            this.rpId = this@GenerateRegistrationOptionsTest.rpId
            base64Challenge = this@GenerateRegistrationOptionsTest.challenge
            this.userName = this@GenerateRegistrationOptionsTest.userName
        }

        assertEquals(options.pubKeyCredParams[0].alg, -8)
    }

    @Test
    fun shouldRaiseErrorIfStringIsSpecifiedForUserID() {
        val exception = assertFailsWith<IllegalArgumentException> {
            generateRegistrationOptions {
                this.rpName = this@GenerateRegistrationOptionsTest.rpName
                this.rpId = this@GenerateRegistrationOptionsTest.rpId
                this.userName = this@GenerateRegistrationOptionsTest.userName
                this.base64UserID = "customUserID"
            }
        }
        assertEquals(
            exception.message,
            "String values for `userID` are no longer supported. See https://simplewebauthn.dev/docs/advanced/server/custom-user-ids"
        )
    }
}