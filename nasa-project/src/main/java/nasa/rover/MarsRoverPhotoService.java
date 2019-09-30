package nasa.rover;

import nasa.pojos.MarsRoverPhotoResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class MarsRoverPhotoService {
    private static final String BASE_URI = "https://api.nasa.gov/mars-photos/api/v1/rovers";
    private String rover = "curiosity";
    private int sol = 1000;
    private String earthDate = "2015-05-30";
    private Cameras camera = Cameras.ALL;
    private String API_KEY;

    public enum Cameras {FHAZ, RHAZ, MAST, CHEMCAM, MAHLI, MARDI, NAVCAM, ALL}

    public MarsRoverPhotoService(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    public static String getBaseUri() {
        return BASE_URI;
    }

    public String getRover() {
        return rover;
    }

    public void setRover(String rover) {
        this.rover = rover;
    }

    public int getSol() {
        return sol;
    }

    public void setSol(int sol) {
        this.sol = sol;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }

    public Cameras getCamera() {
        return camera;
    }

    public void setCamera(Cameras camera) {
        this.camera = camera;
    }

    public MarsRoverPhotoResponse getPhotosBySol() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("sol", sol);
        return doGetPhotos(queryParams);
    }

    public MarsRoverPhotoResponse getPhotosByEarthDate() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("earth_date", earthDate);
        return doGetPhotos(queryParams);
    }

    public MarsRoverPhotoResponse doGetPhotos(Map<String,Object> queryParams){
        queryParams.put("api_key", API_KEY);
        if(camera!=Cameras.ALL){
            queryParams.put("camera", camera.name().toLowerCase());
        }
        return given().baseUri(BASE_URI)
                .queryParams(queryParams)
                .log().method().and().log().uri()
                .when()
                .get(String.format("/%s/photos",rover))
                .then()
                .log()
                .status()
                .and()
                .extract().as(MarsRoverPhotoResponse.class);
    }
}
