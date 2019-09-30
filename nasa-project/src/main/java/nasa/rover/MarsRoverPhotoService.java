package nasa.rover;

import nasa.pojos.MarsRoverPhotoResponse;
import static io.restassured.RestAssured.*;

public class MarsRoverPhotoService {
    private static final String BASE_URI = "https://api.nasa.gov/mars-photos/api/v1/rovers";
    private String rover = "curiosity";
    private int sol = 1000;
    private String earthDate = "2015-05-30";
    private String API_KEY = "c4EkBzgFB3KLDpYdAg7PB57hGQL2E4dWpAcgRN3Q";    //TODO: fetch from somewhere secure

    MarsRoverPhotoResponse getPhotosBySol() {
        return given().baseUri(BASE_URI)
                .queryParam("sol", sol)
                .queryParam("api_key", API_KEY)
                .when()
                .get(String.format("/%s/photos",rover))
                .then()
                .log()
                .everything()
                .and()
                .extract().as(MarsRoverPhotoResponse.class);
    }

    MarsRoverPhotoResponse getPhotosByEarthDate() {
        return given().baseUri(BASE_URI)
                .queryParam("earth_date", earthDate)
                .queryParam("api_key", API_KEY)
                .when()
                .get(String.format("/%s/photos",rover))
                .then()
                .log()
                .everything()
                .and()
                .extract().as(MarsRoverPhotoResponse.class);
    }
}
