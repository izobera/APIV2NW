
import io.restassured.RestAssured;
import org.junit.Test;
import static main.core.Properties.getApiKey;
import static main.core.Properties.getIdWrong;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class ArticleTest {

    private final static String apiUrl = "http://article-api.gazeta.pl/article/v2/id/";
    private  final static int idCorrect =16982063;
    private  final static int idSectionCorrect =141907;

    @Test
    public void shouldGetArticleMetadata() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+idCorrect+ "/section/"+idSectionCorrect+"/metadata" +getApiKey())
                .then()
                .statusCode(200)
                .body("$", hasKey("id"))
                .body("$", hasKey("title"))
                .body("$", hasKey("url"))
                .body("$", hasKey("sectionId"))
                .log()
                .all();
    }

    @Test
    public void shouldGetArticleMetadataWithMainPublication() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+idCorrect+ "/metadata" +getApiKey())
                .then()
                .statusCode(200)
                .body("$", hasKey("id"))
                .body("$", hasKey("title"))
                .body("$", hasKey("url"))
                .body("$", hasKey("sectionId"))
                .log()
                .all();
    }

    @Test
    public void shouldRespondCorrectlyOnArticleNotFound() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+getIdWrong()+ "/section/"+idSectionCorrect+"/metadata" + getApiKey())
                .then()
                .statusCode(404)
                .body("$", hasKey("msg"))
                .log()
                .all();
    }

    @Test
    public void shouldRespondCorrectlyOnArticleNotFoundForMainPublication() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get(apiUrl+getIdWrong()+ "/metadata" +getApiKey())
                .then()
                .statusCode(404)
                .body("$", hasKey("msg"))
                .log()
                .all();
    }

    @Test
    public void shouldGetMobiArticle() {

        RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .when()
                .get()
    }
}
