package steps;

import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import utility.APISetup;
import static org.testng.AssertJUnit.*;


public class apiStep {

    APISetup api = new APISetup();
    APIResponse response;
    String payload;
    String getResult;


    @Given("GET request on end url {string} and query param {string}")
    public void getRequestOnEndUrlAndQueryParam(String endurl, String param) {
        System.out.println("Ashish");
        response = api.getMethod(endurl,api.getQuery(param));
        this.getResult=response.text();
    }










    @And("verify API status code equals {int}")
    public void verifyAPIStatusCodeEquals(int arg0) {
    }


}
