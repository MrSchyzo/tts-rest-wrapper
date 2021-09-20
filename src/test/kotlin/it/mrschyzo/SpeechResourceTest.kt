package it.mrschyzo

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.Test
import javax.ws.rs.core.MediaType

@QuarkusTest
class SpeechResourceTest {

    @Test
    fun testUrlEndpoint() {
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body("Ciao amici")
        .`when`()
            .post("/speak/url")
        .then()
            .statusCode(200)
            .body(startsWith("https://ttsmp3.com/created_mp3"))
            .body(endsWith(".mp3"))
    }

    @Test
    fun testSpeakEndpoint() {
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body("Ciao amici")
        .`when`()
            .post("/speak")
        .then()
            .statusCode(200)
            .body(`is`(not(emptyArray<ByteArray>())))
    }

}