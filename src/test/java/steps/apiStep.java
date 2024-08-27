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
    public void get_request_on_end_url_and_query_param(String endurl, String param) {
        System.out.println("Ashish");
        response = api.getMethod(endurl,api.getQuery(param));
        System.out.println(response);
        this.getResult=response.text();
    }
    @Given("verify API status code equals {int}")
    public void verify_api_status_code_equals(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }








}
