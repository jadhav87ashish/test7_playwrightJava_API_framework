package steps;

import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIResponse;
import config.Settings;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import utility.APISetup;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class apisStep {
    APISetup api = new APISetup();
    APIResponse result;
    String firstJson;

    String getResult;

    String flag;



    APISetup apiSetUp = new APISetup();

    @Given("GET end URL {string} and Query parameter {string}")
    public void endURLAndQueryParameterAndBody(String endURl, String queryParameter) {
        System.out.println(endURl + api.getQuery(queryParameter)+"test");
        result = api.getMethod(endURl, api.getQuery(queryParameter));
        System.out.println(result.text());
        this.getResult = result.text();

    }
    @Given("GET end URL {string} with Query parameter {string}")
    public void getEndURLWithQueryParameter(String endURl, String queryParameter) {
        System.out.println(endURl + queryParameter);
        result = api.getMethod(endURl,queryParameter );
        System.out.println(result.text());
        this.getResult = result.text();
    }
    @Given("GET end URL {string} without Query parameter")
    public void getEndURLWithoutQueryParameter(String endURl) {
        result = api.getMethodWithoutParam(endURl);
        System.out.println(result.text());
        this.getResult = result.text();
    }


    @And("check {int} status code")
    public void checkStatusCode(int arg0) {

        assertEquals("Status code is not matching", arg0, result.status());
        Allure.addAttachment("Status code Error", "Status code is not matching");

    }

    @And("validate Response for {string} is {string}")
    public void validateResponseForIs(String path, String expectedResult) {
        boolean output;
        boolean flag = Boolean.parseBoolean(expectedResult);
        if (flag) {
            output = JsonPath.read(result.text(), "$" + path);
            assertTrue("Given data is not matching", output);
        } else {
            if (expectedResult.equalsIgnoreCase("true")) {
                output = JsonPath.read(result.text(), "$" + path);
                assertTrue("Given data is not matching", output);
            }
            if (expectedResult.equalsIgnoreCase("false")) {
                output = JsonPath.read(result.text(), "$" + path);
                assertFalse("Given data is not matching", output);
            } else {
                assertEquals("Given data is not matching", expectedResult, JsonPath.read(result.text(), "$" + path));
            }
        }
    }


    @Given("get scheme {string}")
    public void getScheme(String pathName) throws IOException {
        firstJson = api.getSchema(pathName);
    }

    @And("update schema for {string} is {string}")
    public void updateSchemaForIs(String jsonPath, String jsonValue) {
        firstJson = api.updateExistingSchema(firstJson, jsonPath, jsonValue);
        System.out.println(firstJson);
    }



    @And("update schema for {string} with {string}")
    public void updateSchemaForWith(String jsonPath, String jsonValue) {
        firstJson = api.updateSchema(firstJson, jsonPath, jsonValue);
    }

    @Given("POST end URL {string} and query parameter")
    public void postMethod(String endURl) {
        System.out.println("Param Appended" + Settings.requiredParam);
        Allure.addAttachment("Payload:", firstJson);
        result = api.postMethod(endURl, Settings.requiredParam, firstJson);
        System.out.println(result.text());
        Allure.addAttachment("Response:", result.text());
        this.getResult = result.text();
    }

    @When("POST end URL {string} without query parameter")
    public void postEndURLWithoutQueryParameter(String endURl) {
        Allure.addAttachment("Payload:", firstJson);
        result = api.postMethodWithoutParam(endURl, firstJson);
        System.out.println(result.text());
        Allure.addAttachment("Response:", result.text());
        this.getResult = result.text();
    }

    @Then("validate Response for {string} is {string} value")
    public void validateResponseFor(String path, String expectedResult) {
        Number finalValue = api.checkGivenValueIsInteger(expectedResult);
        assertEquals("Given data is not matching", finalValue, JsonPath.read(result.text(), "$" + path));
    }

    @Then("validate Response count for {string} is greater than {int}")
    public void validateResponseCountForIsGreaterThan(String path, Integer counter) {
        Object output = JsonPath.read(result.text(), "$" + path + "[*]._id");
        List<String> list = List.of(output.toString().split(","));
        assertTrue("There is no document in the list", list.size() > counter);
    }

    @Given("Delete end URL {string} and query parameter {string} and pass above API response Data {string}")
    public void deleteEndURLAndQueryParameterAndPassAboveAPIResponseData(String endUrl, String queryParameter, String path) {
        String queryParam;
        if (queryParameter.length() == 1) {
            queryParam = Settings.requiredParam;
        } else {
            queryParam = Settings.requiredParam + "&" + queryParameter;
        }
        Object extractedData = JsonPath.read(result.text(), "$" + path);
        result = api.deleteMethod(endUrl, queryParam, extractedData);
        System.out.println(result.text());
        Allure.addAttachment("response:", result.text());
    }

    @Then("validate Response for {string} is boolean")
    public void validateResponseForIsBoolean(String path) {
        Object valueObject = JsonPath.read(result.text(), "$" + path);
        if (valueObject instanceof Boolean) {
            Boolean value = (Boolean) valueObject;
        } else if (valueObject instanceof List) {
            List<Boolean> values = (List<Boolean>) valueObject;
            for (Boolean valueList : values) {
                Boolean value = valueList;
            }
        } else {
            throw new RuntimeException("Field is not boolean");
        }
    }

    @Then("validate Response for {string} is int")
    public void validateResponseForIsInt(String path) {
        Object value = JsonPath.read(result.text(), "$" + path);
        if (value instanceof Integer) {
            boolean b = value instanceof Integer;
            assertTrue(b);
        } else if (value instanceof List) {
            List<Integer> list = JsonPath.read(result.text(), path);
            for (Integer item : list) {
                boolean b = item instanceof Integer;
                assertTrue(b);
                assertNotNull(item);
            }
        } else {
            throw new RuntimeException("Field is not Integer");
        }

    }

    @Then("validate Response for {string} is {int}")
    public void validateResponseForIs(String path, int num) {
        Object value = JsonPath.read(result.text(), "$" + path);
        int intValue;
        boolean b = value instanceof Integer;
        if (value instanceof Integer) {  // Check if the Object is an instance of Integer
            intValue = (Integer) value;  // Unbox the Integer object to int
            if (intValue == num) {  // Compare the unboxed value with the int
                assertTrue(b);
            } else {
                throw new RuntimeException("The values are not equal.");
            }
        } else {
            throw new RuntimeException("The object is not an Integer.");
        }

    }

    @Then("validate Response for {string} is String")
    public void validateResponseForIsString(String path) {
        Object value = JsonPath.read(result.text(), "$" + path);
        if (value instanceof String) {
            boolean b = value instanceof String;
            assertTrue(b);
        } else if (value instanceof List) {
            List<String> list = JsonPath.read(result.text(), path);
            for (String item : list) {
                boolean b = item instanceof String;
                assertTrue(b);
                assertNotNull(item);
            }
        } else {
            throw new RuntimeException("Field is not String");
        }
    }

    @And("update schema for {string} {string} with {string}")
    public void updateSchemaStartEndDate(String jsonPath, String jsonPath2, String jsonValue) {
        firstJson = api.updateSchemaStartEndDate(firstJson, jsonPath, jsonPath2, jsonValue);
    }

    @Given("PUT end URl {string} and query parameter")
    public void putEndURlAndQueryParameter(String endURl) {
        System.out.println(" bot id is  " + Settings.requiredParam);
        result = api.putMethod(endURl, Settings.requiredParam, firstJson);
        System.out.println(result.text());
        Allure.addAttachment("response:", result.text());
        this.getResult = result.text();
    }

    @Given("POST end URL {string} and query parameter {string} and pass above API response Data {string}")
    public void postEndURLAndQueryParameterAndPassAboveAPIResponseData(String endURl, String queryParameter, String path) {
//        String queryParam;
//        if (queryParameter.length() == 1) {
//            queryParam = Settings.apiBotID;
//        } else {
//            queryParam = Settings.apiBotID + "&" + queryParameter;
//        }
        result = api.postMethod(endURl, api.getQuery(queryParameter), JsonPath.read(result.text(), "$" + path));
        System.out.println(result.text());
        Allure.addAttachment("response:", result.text());
    }

    @Given("POST end URL {string} and query parameter {string} and pass above API response Data {string} for scheme {string}")
    public void postEndURLAndQueryParameterAndPassAboveAPIResponseDataForScheme(String endURl, String queryParameter, String path, String pathName) throws IOException {

        firstJson = api.getSchema(pathName);
        result = api.postMethodForDynamicURL(endURl, api.getQuery(queryParameter), JsonPath.read(result.text(), "$" + path), firstJson);
        System.out.println(result.text());
        Allure.addAttachment("response:", result.text());
    }

    @Given("PUT end URL {string} and query parameter {string} and pass above API response Data {string} for scheme {string}")
    public void putEndURLAndQueryParameterAndPassAboveAPIResponseDataForScheme(String endURl, String queryParameter, String path, String pathName) {

        result = api.putMethodForDynamicURL(endURl, api.getQuery(queryParameter), JsonPath.read(result.text(), "$" + path), firstJson);
        Allure.addAttachment("response:", result.text());
    }

    @And("update schema for {string} from above API response for {string}")
    public void updateSchemaForFromAboveAPIResponseFor(String path, String updatedPath) {
        firstJson = api.updateExistingSchemaWithResult(firstJson, path, result, updatedPath);
    }


    @Given("PUT end URl {string} and Query parameter {string}")
    public void putEndURlAndQueryParameter(String endURl, String queryParameter, String schema) {
        System.out.println(endURl + api.getQuery(queryParameter));

        result = api.putMethod(endURl, api.getQuery(queryParameter), schema);
        System.out.println(result.text());
        this.getResult = result.text();
    }

    @Given("PATCH end URL {string} and query parameter {string} and pass above API response Data {string}")
    public void patchEndURLAndQueryParameterAndPassAboveAPIResponseDataForScheme(String endUrl, String queryParameter, String path) {
        String queryParam;
        if (queryParameter.length() == 1) {
            queryParam = Settings.requiredParam;
        } else {
            queryParam = Settings.requiredParam + "&" + queryParameter;
        }
        Object extractedData = JsonPath.read(result.text(), "$" + path);
        result = api.patchMethod(endUrl, queryParam, extractedData, firstJson);
        System.out.println(result.text());
        Allure.addAttachment("response:", result.text());
    }
    @Given("user login")
    public void userLogin() {
        System.out.println("Ashish");
    }



}
