package it.mrschyzo.domain

import io.quarkus.test.junit.QuarkusTest
import it.mrschyzo.domain.speech.SpeechParams
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.enterprise.inject.Default
import javax.inject.Inject

@QuarkusTest
class ItalianSpeechSimplifierTest {

    // SEE: https://quarkus.io/guides/kotlin#cdi-inject-with-kotlin
    @Inject
    @field:Default
    lateinit var simplifier: ItalianSpeechSimplifier

    /*
     * Test utilities
     */
    private fun simplifiedTextOf(input: String): String = simplifier.simplify(input.asSpeech()).text
    private fun String.asSpeech(isMale: Boolean = true): SpeechParams = SpeechParams(text = this, isMale = isMale)
    private infix fun String.`should be simplified into`(expected: String) = assertEquals(expected, simplifiedTextOf(this))

    @Test
    fun `it renders the input string as lower case`() {
        "TrImMEd WorD" `should be simplified into` "trimmed word"
    }

    @Test
    fun `it trims spaces`() {
        " Trimmed Word " `should be simplified into` "trimmed word"
    }

    @Test
    fun `it compacts 3+ characters sequences into 2 characters`() {
        "Trimmmed Word" `should be simplified into` "trimmed word"
    }

    @Test
    fun `it compacts 2+ spaces sequences into a singleton`() {
        "Trimmmed Word" `should be simplified into` "trimmed word"
    }

    @Test
    fun `it transforms non-alphabetic characters into spaces`() {
        "Trimmed!word" `should be simplified into` "trimmed word"
    }

    @Test
    fun `transformed non-alphabetic characters are trimmed`() {
        "!Trimmmed Word!" `should be simplified into` "trimmed word"
    }

    @Test
    fun `transformed non-alphabetic characters are compacted into a single space if adjacent`() {
        "Trimmed-!_][-Word" `should be simplified into` "trimmed word"
    }
}
