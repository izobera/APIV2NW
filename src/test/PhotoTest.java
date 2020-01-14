import io.restassured.RestAssured;
import org.junit.Test;
import static main.core.Properties.getApiKey;
import static main.core.Properties.getIdWrong;
import static org.hamcrest.core.StringContains.containsString;

public class PhotoTest {

    private final static String apiUrl = "http://photo-api.gazeta.pl/photo/v2/";
    private final static String idCorrect = "19279347";

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
                .body(containsString("id"))
                .body(containsString("section"))
                .body(containsString("url"))
                .body(containsString("title"))
                .body(containsString("signature"))
                .body(containsString("file"))
                .log()
                .all();
    }

    @Test
    public void shouldRespondCorrectlyOnPhotoNotFound() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+getIdWrong()+getApiKey())
                .then()
                .statusCode(404)
                .assertThat()
                .body(containsString("status"))
                .log()
                .all();
    }
}
