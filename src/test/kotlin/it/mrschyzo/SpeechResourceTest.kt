package it.mrschyzo

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.apache.commons.io.FileUtils.delete
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
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

        // See application.properties to check why tmp is emptied
        Files.walk(Path.of("tmp"))
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete)
    }
}
