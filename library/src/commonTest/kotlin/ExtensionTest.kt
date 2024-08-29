import extensions.decodeToUTF8
import extensions.encodeToBase64URLString
import extensions.encodeToUByteArray
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
}

