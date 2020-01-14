
import io.restassured.RestAssured;
import org.junit.Test;

import static main.core.Properties.getApiKey;
import static main.core.Properties.getIdWrong;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class GalleryTest {

    private final static String apiUrl = "http://gallery-api.gazeta.pl/gallery/v2/";
    private  final static int idCorrectGallery =22111422;
    private  final static int idCorrectPhoto =19279347;

    @Test
    public void shouldGetGallery() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+"id/"+idCorrectGallery+getApiKey())
                .then()
                .statusCode(200)
                .body("$", hasKey("photos"))
                .log()
                .all();
    }

    @Test
    public void shouldGetPhoto() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+"photo/"+ idCorrectPhoto+getApiKey())
                .then()
                .statusCode(200)
                .body("$", hasKey("id"))
                .log()
                .all();
    }

    @Test
    public void shouldRespondCorrectlyOnGalleryNotFound() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+"id/"+getIdWrong()+getApiKey())
                .then()
                .statusCode(404)
                .body("$", hasKey("status"))
                .body("status.details", containsString("Cannot fetch any of gallery photos"))
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
                .get(apiUrl+"photo/"+ getIdWrong()+getApiKey())
                .then()
                .statusCode(404)
                .body("$", hasKey("status"))
                .body("status.details", containsString("Photo " + getIdWrong() + " not found"))
                .log()
                .all();
    }
}