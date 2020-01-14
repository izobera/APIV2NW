import io.restassured.RestAssured;
import org.junit.Test;
import static main.core.Properties.getApiKey;
import static main.core.Properties.getIdWrong;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class RelatedTest {

    private final static String apiUrl = "http://related-api.gazeta.pl/related/v2/assetId/";
    private  final static int idCorrect =19771372;

    @Test
    public void shouldGetRelated() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+idCorrect+getApiKey())
                .then()
                .statusCode(200)
                .body("$", hasKey("articleRelated"))
                .log()
                .all();
    }

    @Test
    public void shouldRespondWithEmptyRelated() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+getIdWrong()+getApiKey())
                .then()
                .statusCode(200)
                .body("$", hasKey("articleRelated"))
                .log()
                .all();
    }
}
