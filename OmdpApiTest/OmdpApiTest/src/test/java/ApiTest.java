import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Movie;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiTest {

  public static final String API_KEY = "7e497b12";
  public static final String SEARCH_URL = "https://www.omdbapi.com/";
  public static final String SEARCH_HARRYPOTTER = "harry potter";
  private Movie harryPotherMovie = null;

  @Test
  public void testSearch() {

    RestAssured.baseURI = SEARCH_URL;
    Response res = getResponse("s", SEARCH_HARRYPOTTER);

    Assert.assertEquals(res.statusCode(), 200);

    List<Map<String, String>> movies = res.jsonPath().getList("Search");

    String imdbID = "";

    // we get the hashmap to parse the json using key value
    // if the title is equals to that movie just get the id to initialize the movie object to use it
    for (Map<String, String> movie : movies) {

      if (movie.get("Title").equalsIgnoreCase("Harry Potter and the Sorcerer's Stone")) {
        imdbID = movie.get("imdbID");
        harryPotherMovie =
            new Movie(
                movie.get("title"),
                movie.get("year"),
                movie.get("title"),
                movie.get("type"),
                movie.get("poster"));
        break;
      }
    }

    System.out.println(imdbID);
  }

  @Test
  public void testSearch2() {

    RestAssured.baseURI = SEARCH_URL;

    if (harryPotherMovie.getImdbID() != null) {

      Response res = getResponse("i", harryPotherMovie.getImdbID());
      Assert.assertEquals(res.statusCode(), 200);

      List<Map<String, String>> movies = res.jsonPath().getList("Search");
      Assert.assertEquals(res.statusCode(), 200);
      // we check the response is fine here because movies is not null as we know
      Assert.assertNotNull(movies);
    }
  }

  public Response getResponse(String paramKey, String paramValue) {

    RestAssured.baseURI = SEARCH_URL;
    return given()
        .param("apikey", API_KEY)
        .param(paramKey, paramValue)
        .get()
        .then()
        .extract()
        .response();
  }
}
