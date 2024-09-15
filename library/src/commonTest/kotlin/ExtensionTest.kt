import extensions.decodeToUTF8
import extensions.encodeToBase64URLString
import extensions.encodeToUByteArray
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.cbor.Cbor
import models.AttestationObject
import models.decodeAttestationObject
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalUnsignedTypes::class)
class ExtensionTest {

    private val base64URLJSON = "eyJ0eXBlIjoid2ViYXV0aG4uY3JlYXRlIiwiY2hhbGxlbmdlIjoiZXlKaFkzUjFZV3hEYUdGc2JHVnVaMlVpT2lKNFVuTlpaRU5SZGpWWFdrOXhiWGhTWldsYWJEWkRPWEUxVTJaeVdtNWxOR3hPVTNJNVVWWjBVR2xuSWl3aVlYSmlhWFJ5WVhKNVJHRjBZU0k2SW1GeVltbDBjbUZ5ZVVSaGRHRkdiM0pUYVdkdWFXNW5JbjAiLCJvcmlnaW4iOiJodHRwOi8vbG9jYWxob3N0OjgwMDAiLCJjcm9zc09yaWdpbiI6ZmFsc2V9"
    private val decodedJSON = "{\"type\":\"webauthn.create\",\"challenge\":\"eyJhY3R1YWxDaGFsbGVuZ2UiOiJ4UnNZZENRdjVXWk9xbXhSZWlabDZDOXE1U2ZyWm5lNGxOU3I5UVZ0UGlnIiwiYXJiaXRyYXJ5RGF0YSI6ImFyYml0cmFyeURhdGFGb3JTaWduaW5nIn0\",\"origin\":\"http://localhost:8000\",\"crossOrigin\":false}"
    private val randomString = "randomstringtoencode"
    private val base64URLString = "cmFuZG9tc3RyaW5ndG9lbmNvZGU="
    private val intList = listOf(114, 97, 110, 100, 111, 109, 115, 116, 114, 105, 110, 103, 116, 111, 101, 110, 99, 111, 100, 101)
    private val ubyteArray = intList.map { it.toUByte() }.toUByteArray()

    @Test
    fun convertBase64URLStringToUTF8String() {
        val decoded = base64URLJSON.decodeToUTF8()
        assertEquals(decoded, decodedJSON)
    }

    @Test
    fun convertUTF8StringToBase64URLString() {
        val encoded = randomString.encodeToBase64URLString()
        assertEquals(base64URLString, encoded)
    }

    @Test
    fun convertBase64URLStringToUByteArray() {
        val byteArray = base64URLString.encodeToUByteArray()
        byteArray.forEachIndexed { index, byte ->
            assertEquals(ubyteArray[index], byte)
        }
    }

    @Test
    fun convertUByteArrayToBase64URLString() {
        val base64URLString = ubyteArray.encodeToBase64URLString()
        assertEquals(base64URLString, base64URLString)
    }

    @Test
    fun convertUByteArrayToUTF8String() {
        val decoded = ubyteArray.asByteArray().decodeToString()
        assertEquals(randomString, decoded)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun exp() {
        val attest = "o2NmbXRoZmlkby11MmZnYXR0U3RtdKJjc2lnWEcwRQIgRYUftNUmhT0VWTZmIgDmrOoP26Pcre-kL3DLnCrXbegCIQCOu_x5gqp-Rej76zeBuXlk8e7J-9WM_i-wZmCIbIgCGmN4NWOBWQLBMIICvTCCAaWgAwIBAgIEKudiYzANBgkqhkiG9w0BAQsFADAuMSwwKgYDVQQDEyNZdWJpY28gVTJGIFJvb3QgQ0EgU2VyaWFsIDQ1NzIwMDYzMTAgFw0xNDA4MDEwMDAwMDBaGA8yMDUwMDkwNDAwMDAwMFowbjELMAkGA1UEBhMCU0UxEjAQBgNVBAoMCVl1YmljbyBBQjEiMCAGA1UECwwZQXV0aGVudGljYXRvciBBdHRlc3RhdGlvbjEnMCUGA1UEAwweWXViaWNvIFUyRiBFRSBTZXJpYWwgNzE5ODA3MDc1MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEKgOGXmBD2Z4R_xCqJVRXhL8Jr45rHjsyFykhb1USGozZENOZ3cdovf5Ke8fj2rxi5tJGn_VnW4_6iQzKdIaeP6NsMGowIgYJKwYBBAGCxAoCBBUxLjMuNi4xLjQuMS40MTQ4Mi4xLjEwEwYLKwYBBAGC5RwCAQEEBAMCBDAwIQYLKwYBBAGC5RwBAQQEEgQQbUS6m_bsLkm5MAyP6SDLczAMBgNVHRMBAf8EAjAAMA0GCSqGSIb3DQEBCwUAA4IBAQByV9A83MPhFWmEkNb4DvlbUwcjc9nmRzJjKxHc3HeK7GvVkm0H4XucVDB4jeMvTke0WHb_jFUiApvpOHh5VyMx5ydwFoKKcRs5x0_WwSWL0eTZ5WbVcHkDR9pSNcA_D_5AsUKOBcbpF5nkdVRxaQHuuIuwV4k1iK2IqtMNcU8vL6w21U261xCcWwJ6sMq4zzVO8QCKCQhsoIaWrwz828GDmPzfAjFsJiLJXuYivdHACkeJ5KHMt0mjVLpfJ2BCML7_rgbmvwL7wBW80VHfNdcKmKjkLcpEiPzwcQQhiN_qHV90t-p4iyr5xRSpurlP5zic2hlRkLKxMH2_kRjhqSn4aGF1dGhEYXRhWMQ93EcQ6cCIsinbqJ1WMiC7Ofcimv9GWwplaxr7mor4oEEAAAAAAAAAAAAAAAAAAAAAAAAAAABAVHzbxaYaJu2P8m1Y2iHn2gRNHrgK0iYbn9E978L3Qi7Q-chFeicIHwYCRophz5lth2nCgEVKcgWirxlgidgbUaUBAgMmIAEhWCDIkcsOaVKDIQYwq3EDQ-pST2kRwNH_l1nCgW-WcFpNXiJYIBSbummp-KO3qZeqmvZ_U_uirCDL2RNj3E5y4_KzefIr"
        val byteArray = attest.encodeToUByteArray().asByteArray()
        decodeAttestationObject(byteArray)
    }
}

