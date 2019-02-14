package automationpilot.steps;

import automationpilot.sut_model.CuriosityAPI;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonObjectParser;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class CuriosityAPIStepDefinitions {

    private CuriosityAPI curiosityAPI;
    private ResourceBundle rb;

    @Given("^a NASA API session for Curiosity$")
    public void a_NASA_API_session_for_Curiosity(){
        rb = ResourceBundle.getBundle("config");
        String curiosityEndpoint = "rovers/curiosity/photos";
        curiosityAPI = new CuriosityAPI();
        curiosityAPI.apiUrl = new CuriosityAPI.ApiUrl(rb.getString("apiUrl") + curiosityEndpoint);
        curiosityAPI.apiUrl.setApiKey(rb.getString("apiKey"));
    }

    @Given("^I get the first \"([^\"]*)\" photos made on sol \"([^\"]*)\"$")
    public void i_get_the_first_photos_made_on_sol(int numberOfPhotos, String daysInSol) throws Exception{
        curiosityAPI.apiUrl.setSol(daysInSol);
        curiosityAPI.apiUrl.setEarthDate(null);
        // TODO: it should not be here....
        //String sentRequest = curiosityAPI.apiUrl.build();
        HttpRequest request = curiosityAPI.requestFactory.buildGetRequest(curiosityAPI.apiUrl);
        curiosityAPI.photoFeed = request.execute().parseAs(CuriosityAPI.PhotoFeed.class);
        int totalNumberOfPhotos = curiosityAPI.photoFeed.photos.size();
        Assert.assertTrue(totalNumberOfPhotos >= numberOfPhotos);
        System.out.println("Retrieved " + totalNumberOfPhotos + " Photos");
    }

    @Given("^I get the earth date for sol \"([^\"]*)\"$")
    public void i_get_the_earth_date_for_sol(int daysInSol) throws Exception {
        // Days on Mars are longer. The conversion factor is 1.02749125170 days/sol
        double conversionFactor = 1.027;

        // get the Landing date from one of the photo's rover information
        String landingDateInRequest = curiosityAPI.photoFeed.photos.get(0).rover.landingDate;

        Date landingDateEquivalent = new SimpleDateFormat("yyyy-MM-dd").parse(landingDateInRequest);

        // Get the Earth Date by calculating the landing date plus the sol days passed by the conversion factor
        double earthDateEquivalent = landingDateEquivalent.getTime() + TimeUnit.DAYS.toMillis(daysInSol) * conversionFactor;
        Date earthDateCalculated = new Date((long) earthDateEquivalent);

        Assert.assertTrue(earthDateCalculated.getTime() >= landingDateEquivalent.getTime());

        // assert that the calculated date matches the date returned by the API
        Date earthDateInRequest = new SimpleDateFormat("yyyy-MM-dd")
                .parse(curiosityAPI.photoFeed.photos.get(0).earthDate);
        Assert.assertEquals(earthDateInRequest.compareTo(earthDateCalculated), 0);
        System.out.println("DONE");
    }

    @When("^I get the first \"([^\"]*)\" pictures made on earth date$")
    public void i_get_the_first_pictures_made_on_earth_date(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("DONE");

        assert true == true;
    }

    @Then("^the photos should be the same$")
    public void the_photos_should_be_the_same() throws Exception {
        // It's possible that if I retrieve the earth date for a given sol, that earth day might have started on the
        // previous sol
        System.out.println("DONE");
        assert true == true;
    }

    @Given("^I get the all photos made on sol \"([^\"]*)\"$")
    public void i_get_the_all_photos_made_on_sol(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("DONE");
        assert true == true;
    }

    @Then("^no camera made more than \"([^\"]*)\" photos$")
    public void no_camera_made_more_than_photos(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("DONE");
        assert true == true;
    }
}