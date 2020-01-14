import io.restassured.RestAssured;
import org.junit.Test;
import static main.core.Properties.getApiKey;
import static main.core.Properties.getIdWrong;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class VideoTest {

    private final static String apiUrl = "http://document-video-api.gazeta.pl/video/v2/id/";
    private  final static int idCorrect =21667888;

    @Test
    public void shouldReturnCorrectProductList() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+idCorrect+getApiKey())
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", hasKey("id"))
                .body("$", hasKey("title"))
                .body("params", hasKey("distributor"))
                .body("id", comparesEqualTo(idCorrect))
                .log()
                .all();
    }

    @Test
    public void shouldRespondCorrectlyOnVideoNotFound() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+getIdWrong()+getApiKey())
                .then()
                .statusCode(404)
                .assertThat()
                .body("$", hasKey("status"))
                .log()
                .all();
    }
}
