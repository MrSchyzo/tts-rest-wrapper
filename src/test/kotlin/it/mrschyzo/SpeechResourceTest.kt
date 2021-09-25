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
            .post("/speak")
        .then()
            .statusCode(200)
            .body(endsWith(".mp3"))
    }

}