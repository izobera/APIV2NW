import io.restassured.RestAssured;
import org.junit.Test;
import static main.core.Properties.getApiKey;
import static main.core.Properties.getIdWrong;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class PhotostoryTest {

    private final static String apiUrl = "http://photostory-api.gazeta.pl/photostory/v2/";
    private  final static int idCorrect =8461274;
    private  final static int idCorrectSection =79592;

    @Test
    public void shouldGetDocument() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+"id/"+idCorrect+"/section/"+ idCorrectSection+getApiKey())
                .then()
                .statusCode(200)
                .body("$", hasKey("id"))
                .body("$", hasKey("title"))
                .body("$", hasKey("owner"))
                .body("$", hasKey("photostorySlides"))
                .body("$", hasKey("relatedPhotos"))
                .log()
                .all();
    }

    @Test
    public void shouldRespondCorrectlyOnPhotostoryNotFound() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+"id/"+getIdWrong()+"/section/"+ idCorrectSection+getApiKey())
                .then()
                .statusCode(404)
                .body("$", hasKey("status"))
                .log()
                .all();
    }
}
