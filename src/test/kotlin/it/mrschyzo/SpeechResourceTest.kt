package it.mrschyzo

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import javax.ws.rs.core.MediaType

@QuarkusTest
class SpeechResourceTest {

    @Test
    fun testUrlEndpoint() {
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body("""{"text": "Ciao amici", "isMale": true}""")
            .`when`()
            .post("/speak")
            .then()
            .statusCode(200)
            .body(equalTo("ECF039C7BA214A37B66F8AAD644EADDFCFE655021672F25026973A298F30C225"))
    }
}
