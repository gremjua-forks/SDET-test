package automationpilot.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

public class Stepdefs {


    /*
    @Given("a NASA API session for Curiosity")
    public void a_NASA_API_session_for_Curiosity() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        assert true == true;
    }

    @Given("I get the first \"{int}\" photos made on sol \"{int}\"")
    public void i_get_the_first_photos_made_on_sol(int numberOfPhotos, int daysInSol){
        // Write code here that turns the phrase above into concrete actions
        ;
    }*/

    @Given("^a NASA API session for Curiosity$")
    public void a_NASA_API_session_for_Curiosity(){
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Debggging.....");
        assert true == true;
    }

    @Given("^I get the first \"([^\"]*)\" photos made on sol \"([^\"]*)\"$")
    public void i_get_the_first_photos_made_on_sol(int numberOfPhotos, int daysInSol){
        // Write code here that turns the phrase above into concrete actions

        assert true == true;
    }

    @Given("^I get the earth date for sol \"([^\"]*)\"$")
    public void i_get_the_earth_date_for_sol(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        assert true == true;
    }

    @When("^I get the first \"([^\"]*)\" pictures made on earth date$")
    public void i_get_the_first_pictures_made_on_earth_date(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        assert true == true;
    }

    @Then("^the photos should be the same$")
    public void the_photos_should_be_the_same() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        assert true == true;
    }

    @Given("^I get the all photos made on sol \"([^\"]*)\"$")
    public void i_get_the_all_photos_made_on_sol(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        assert true == true;
    }

    @Then("^no camera made more than \"([^\"]*)\" photos$")
    public void no_camera_made_more_than_photos(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        assert true == true;
    }
}