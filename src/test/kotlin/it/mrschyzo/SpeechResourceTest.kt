package it.mrschyzo

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import it.mrschyzo.configuration.STORAGE_FILESYSTEM_ROOT
import it.mrschyzo.utils.extensions.deleteRecursively
import org.eclipse.microprofile.config.ConfigProvider
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import java.nio.file.Path
import javax.ws.rs.core.MediaType

@QuarkusTest
class SpeechResourceTest {

    @Test
    fun testUrlEndpoint() {
        val config = ConfigProvider.getConfig()
        val storageRoot = config.getValue(STORAGE_FILESYSTEM_ROOT, String::class.java)
        val storageRootPath = Path.of(storageRoot)

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body("""{"text": "Ciao amici", "isMale": true}""")
            .`when`()
            .post("/speak")
            .then()
            .statusCode(200)
            .body(equalTo("ECF039C7BA214A37B66F8AAD644EADDFCFE655021672F25026973A298F30C225"))

        storageRootPath.deleteRecursively()
    }
}
