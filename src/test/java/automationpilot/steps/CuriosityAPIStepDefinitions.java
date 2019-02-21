package automationpilot.steps;

import automationpilot.sut_model.CuriosityAPI;
import com.google.api.client.http.HttpRequest;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import logger.utility.CucumberLog;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CuriosityAPIStepDefinitions {

    private CuriosityAPI curiosityAPI;
    private Date earthDateCalculated;
    private List<CuriosityAPI.Photo> photosBySolDaySubSet;
    private List<CuriosityAPI.Photo> photosByEarthDateSubSet;
    private List<CuriosityAPI.Photo> allPhotosBySolDay;


    @Given("^a NASA API session for Curiosity$")
    public void a_NASA_API_session_for_Curiosity() {
        ResourceBundle rb = ResourceBundle.getBundle("config");
        String curiosityEndpoint = "rovers/curiosity/photos";
        curiosityAPI = new CuriosityAPI();
        curiosityAPI.apiUrl = new CuriosityAPI.ApiUrl(rb.getString("apiUrl") + curiosityEndpoint);
        curiosityAPI.apiUrl.setApiKey(rb.getString("apiKey"));
    }

    @Given("^I get the first \"([^\"]*)\" photos made on sol \"([^\"]*)\"$")
    public void i_get_the_first_photos_made_on_sol(int numberOfPhotos, String daysInSol) throws Exception {
        // NOTE: The API does not support a limit for the number of photos retrieved. It does support
        // pagination, but it's fixed to a maximum of 25 photos
        curiosityAPI.apiUrl.setSol(daysInSol);
        curiosityAPI.apiUrl.setEarthDate(null);

        CuriosityAPI.PhotoFeed photoFeed = getPhotosFromAPI(curiosityAPI);
        int totalNumberOfPhotos = photoFeed.photos.size();

        Assert.assertTrue(totalNumberOfPhotos >= numberOfPhotos);
        CucumberLog.info("Retrieved " + totalNumberOfPhotos + " Photos");

        // Save the first 10 photos
        photosBySolDaySubSet = curiosityAPI.photoFeed.photos.subList(0, numberOfPhotos);
    }

    @Given("^I get the earth date for sol \"([^\"]*)\"$")
    public void i_get_the_earth_date_for_sol(int daysInSol) throws Exception {
        // Days on Mars are longer. The conversion factor is 1.02749125170 days/sol
        double conversionFactor = 1.027;

        // get the Landing date from one of the photo's rover information
        String landingDateInRequest = curiosityAPI.photoFeed.photos.get(0).rover.landingDate;

        Date landingDateAPI = new SimpleDateFormat("yyyy-MM-dd").parse(landingDateInRequest);

        // Get the Earth Date by calculating the landing date plus the sol days passed by the conversion factor
        double earthDateEquivalent = landingDateAPI.getTime() + TimeUnit.DAYS.toMillis(daysInSol) * conversionFactor;
        earthDateCalculated = new Date((long) earthDateEquivalent);

        Assert.assertTrue(earthDateCalculated.getTime() >= landingDateAPI.getTime());

        // assert that the calculated date matches the date returned by the API
        Date earthDateInRequest = new SimpleDateFormat("yyyy-MM-dd")
                .parse(curiosityAPI.photoFeed.photos.get(0).earthDate);

        Assert.assertEquals(earthDateInRequest.compareTo(earthDateCalculated), 0);
    }

    @When("^I get the first \"([^\"]*)\" pictures made on that earth date$")
    public void i_get_the_first_pictures_made_on_earth_date(int numberOfPhotos) throws Exception {
        Assert.assertNotNull(earthDateCalculated, "Error. A date for retrieving the pictures should have been defined.");
        String earthDate = new SimpleDateFormat("yyyy-MM-dd").format(earthDateCalculated);

        curiosityAPI.apiUrl.setSol(null);
        curiosityAPI.apiUrl.setEarthDate(earthDate);

        CuriosityAPI.PhotoFeed photoFeed = getPhotosFromAPI(curiosityAPI);
        int totalNumberOfPhotos = photoFeed.photos.size();

        Assert.assertTrue(totalNumberOfPhotos >= numberOfPhotos);
        CucumberLog.info("Retrieved " + totalNumberOfPhotos + " Photos");

        photosByEarthDateSubSet = curiosityAPI.photoFeed.photos.subList(0, numberOfPhotos);
    }

    @Then("^the photos should be the same$")
    public void the_photos_should_be_the_same() throws Exception {
        // TODO: Compare downloaded images and metadata from API. Test fails in case of any difference.
        // It's possible that if I retrieve the earth date for a given sol, that earth day might have started on the
        // previous sol

        Assert.assertEquals(photosBySolDaySubSet.size(), photosByEarthDateSubSet.size(),
                "Error. Sizes do not match.");

        // Verify that the API requests match (have the same fields and values for each)
        for (int i = 0; i < photosBySolDaySubSet.size(); i++) {
            Assert.assertTrue(photosBySolDaySubSet.get(i).equals(photosByEarthDateSubSet.get(i)),
                    "Error. API requests do not match.\n" +
                            "PHOTO BY SOL DAY:\n" + photosBySolDaySubSet.get(i).prettyPrint() + "\n\n" +
                            "PHOTO BY EARTH DATE:\n" + photosByEarthDateSubSet.get(i).prettyPrint() + "\n\n");

            // Compare photos
            URL photoBySolUrl = new URL(photosBySolDaySubSet.get(i).imgSrc);
            BufferedImage photoBySolImage = ImageIO.read(photoBySolUrl);
            URL photoByEarthDateUrl = new URL(photosByEarthDateSubSet.get(i).imgSrc);
            BufferedImage photoByEarthDateImage = ImageIO.read(photoByEarthDateUrl);

            Assert.assertTrue(compareImages(photoBySolImage, photoByEarthDateImage));
        }
        CucumberLog.info("API return values and photos metadata are the same");
    }

    @Given("^I get the all photos made on sol \"([^\"]*)\"$")
    public void i_get_the_all_photos_made_on_sol(String daysInSol) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        // NOTE: The API does not support a limit for the number of photos retrieved. It does support
        // pagination, but it's fixed to a maximum of 25 photos
        curiosityAPI.apiUrl.setSol(daysInSol);
        curiosityAPI.apiUrl.setEarthDate(null);

        CuriosityAPI.PhotoFeed photoFeed = getPhotosFromAPI(curiosityAPI);
        int totalNumberOfPhotos = photoFeed.photos.size();
        CucumberLog.info("Retrieved " + totalNumberOfPhotos + " Photos");

        allPhotosBySolDay = curiosityAPI.photoFeed.photos;
    }

    @Then("^no camera made more than \"([^\"]*)\" photos than any other$")
    public void no_camera_made_more_than_photos(int maxNumberOfPhotosDifference) throws Exception {
        Assert.assertNotNull(allPhotosBySolDay, "Error. There No photo information available to run the test step.");

        // Group photos by the camera used to take it
        HashMap<String, List<CuriosityAPI.CuriosityCamera>> photosGroupedByCamera =
                (HashMap<String, List<CuriosityAPI.CuriosityCamera>>) allPhotosBySolDay.stream()
                        .map(CuriosityAPI.Photo::getCamera)
                        .collect(Collectors.groupingBy(CuriosityAPI.CuriosityCamera::getName));

        for (String key : photosGroupedByCamera.keySet()) {
            CucumberLog.info("Camera: " + key + ". Photos: " + photosGroupedByCamera.get(key).size());
        }


        // Verify that no camera took more than @maxNumberOfPhotosPerCamera photos
        int maxNumberOfPhotosPerCamera = photosGroupedByCamera.values().stream().mapToInt(List::size).max().getAsInt();
        int minNumberOfPhotosPerCamera = photosGroupedByCamera.values().stream().mapToInt(List::size).min().getAsInt();

        int photosTakenDifference = maxNumberOfPhotosPerCamera - minNumberOfPhotosPerCamera;
        Assert.assertTrue(photosTakenDifference < maxNumberOfPhotosDifference,
                "Error. There's a difference of " + photosTakenDifference + " photos between two cameras.");
    }

    private CuriosityAPI.PhotoFeed getPhotosFromAPI(CuriosityAPI curiosityAPI) throws Exception {
        HttpRequest request = curiosityAPI.requestFactory.buildGetRequest(curiosityAPI.apiUrl);
        curiosityAPI.photoFeed = request.execute().parseAs(CuriosityAPI.PhotoFeed.class);
        return curiosityAPI.photoFeed;
    }

    private static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // The photos must be the same size.
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }
        int width = imgA.getWidth();
        int height = imgA.getHeight();
        // Verify each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compare the pixels for equality.
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
}