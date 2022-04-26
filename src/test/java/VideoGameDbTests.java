import config.VideoGameConfig;
import config.VideoGamesEndpoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

public class VideoGameDbTests extends VideoGameConfig {

    @Test
    public void createNewGameByJSON() {
        String gameBodyJson = "{\n" +
                "  \"id\": 16,\n" +
                "  \"name\": \"MyNewGame\",\n" +
                "  \"releaseDate\": \"2012-12-03T10:02:58.768Z\",\n" +
                "  \"reviewScore\": 88,\n" +
                "  \"category\": \"Shooter\",\n" +
                "  \"rating\": \"Mature\"\n" +
                "}";

        Response response = given()
                .body(gameBodyJson).
        when()
                .post(VideoGamesEndpoints.ALL_VIDEO_GAMES);

        response
                .then();
    }

    @Test
    public void getSingleGame() {
        given()
                .pathParam("videoGameId", 5).
        when()
                .get(VideoGamesEndpoints.SINGLE_VIDEO_GAME).
        then();
    }


    @Test
    public void testVideoGameSchemaJSON() {
        given().
                pathParam("videoGameId", 15).
        when().
                get(VideoGamesEndpoints.SINGLE_VIDEO_GAME).
        then().
                body(matchesJsonSchemaInClasspath("VideoGameJsonSchema.json"));
    }

    @Test
    public void convertJSONToPojo() {
        Response response =  given().pathParam("videoGameId", 5).
                when().
                get(VideoGamesEndpoints.SINGLE_VIDEO_GAME);

        VideoGame videoGame = response.getBody().as(VideoGame.class);

        System.out.println(videoGame.toString());
    }

    @Test
    public void assertOnResponseTime() {
        when().
                get(VideoGamesEndpoints.ALL_VIDEO_GAMES).
        then().
                time(lessThan(1000L));
    }
}
