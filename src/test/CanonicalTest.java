
import io.restassured.RestAssured;
import org.junit.Test;

import static main.core.Properties.getApiKey;
import static main.core.Properties.getIdWrong;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class CanonicalTest {

    private final static String apiUrl = "http://canonical-api.gazeta.pl/canonical/v2/id/";
    private  final static int idCorrectGallery =16982063;

    @Test
    public void shouldGetCanonical() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+idCorrectGallery+getApiKey())
                .then()
                .statusCode(200)
                .body("$", hasKey("url"))
                .log()
                .all();
    }

    @Test
    public void shouldRespondCorrectlyOnCanonicalNotFound() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+getIdWrong()+getApiKey())
                .then()
                .statusCode(404)
                .body("$", hasKey("status"))
                .log()
                .all();
    }
}
