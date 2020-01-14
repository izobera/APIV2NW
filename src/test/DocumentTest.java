

import io.restassured.RestAssured;
import org.junit.Test;

import static main.core.Properties.getApiKey;
import static main.core.Properties.getIdWrong;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class DocumentTest {

    private final static String apiUrl = "http://document-api.gazeta.pl/document/v2/document/";
    private final static String apiUrlCache = " http://document-api.gazeta.pl/document/v2/cache/invalidate/";
    private  final static int idCorrect =16982063;

    @Test
    public void shouldGetDocument() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+idCorrect+getApiKey())
                .then()
                .statusCode(200)
                .body("$", hasKey("id"))
                .body("$", hasKey("title"))
                .log()
                .all();
    }

    @Test
    public void shouldRespondCorrectlyOnDocumentNotFound() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+getIdWrong()+getApiKey())
                .then()
                .statusCode(404)
                .body("$", hasKey("message"))
                .log()
                .all();
    }

    @Test
    public void shouldInvalidateDocumentCache() {

        // given document put to cache
        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+idCorrect+getApiKey())
                .then()
                .statusCode(200)
                .log()
                .all();

        // then remove from cache
        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrlCache+idCorrect+getApiKey())
                .then()
                .statusCode(200)
                .body("$", hasKey("removed"))
                .body("removed", is(greaterThanOrEqualTo(1)))
                .log()
                .all();
    }
}
