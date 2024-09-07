package utility;


import base.FrameworkConfig;
import base.TestBase;
import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import config.Settings;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class APISetup {

    APIRequestContext apiRequestContext;
    Properties property;

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }



    public APIRequestContext loginBaseURL() {

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        apiRequestContext = FrameworkConfig.Playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(Settings.URL)
                .setExtraHTTPHeaders(headers));

        return apiRequestContext;
    }


    public String setBaseURL() {
        String baseURL;
        if (Settings.envName.equalsIgnoreCase("preProd")|| Settings.envName.equalsIgnoreCase("prod")|| Settings.envName.equalsIgnoreCase("staging")) {
            baseURL = Settings.URL;
            System.out.println("BaseURL "+baseURL);
        } else {
            baseURL = Settings.URL;
            System.out.println(baseURL);
        }

        return baseURL;
    }


//    public String getToken() {
//        String response = loginBaseURL().post("api/sso/v2login", RequestOptions.create().setData(login.apiLogin())).text();
//        System.out.println("ym_xid=" + JsonPath.read(response, "$" + ".access_token"));
//        return "ym_xid=" + JsonPath.read(response, "$" + ".access_token");
//    }

    public Map<String, String> setHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
//        headers.put("Cookie", getToken());
        headers.put("Content-Type", "application/json");
// headers.put("Cookie","hfuyruitiyot");
// headers.put("x-api-key", "6gk56dkQp3aWVabPLAvi4g_JzVHdw8VE8LbDjAA5");
// headers.put("baggage", "sd-routing-key=b7gkqg7brqb8b,ot-baggage-sd-routing-key:b7gkqg7brqb8b,tracestate:sd-routing-key=b7gkqg7brqb8b,uberctx-sd-routing-key:b7gkqg7brqb8b");
        return headers;
    }

//    public Map<String, String> setInboxHeader() {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        headers.put("x-api-key", Settings.inboxAPIKey);
//        return headers;
//    }

    public APIRequestContext setApiRequestContext() {
        return FrameworkConfig.Playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(setBaseURL()).setExtraHTTPHeaders(setHeader()));
    }
    public Map<String, String> getQueryParameter(String input) {
        Map<String, String> headers = new HashMap<>();
        String[] variables = input.split("&");
        for (String variable : variables) {
            String[] keyValue = variable.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                headers.put(key, value);

            }

        }
        return headers;
    }

    public APIResponse getMethod(String endURl, String input) {
        String[] variables = input.split("&");
        Map<String, String> headers;
        String firstKey;
        String secondKey;
        String thirdKey;
        String fourthKey;
        String formattedURL;
        if (endURl.contains("%s")) {
            formattedURL = String.format(endURl, input);
        } else {
            formattedURL = endURl;
        }
        switch (variables.length) {
            case 1:
                System.out.println("Executing 1st loop");
                String[] parts = input.split("\\W+");
                return setApiRequestContext().get(formattedURL, RequestOptions.create().setQueryParam(parts[0], parts[1]));

            case 2:
                System.out.println("Executing 2nd loop");
                headers = getQueryParameter(input);
                firstKey = headers.keySet().stream().findFirst().orElse(null);
                secondKey = headers.keySet().stream().skip(1).findFirst().orElse(null);
                if (headers.get(firstKey).contains("%s")) {
                    headers.put(firstKey, String.format(headers.get(firstKey), input));
                }
                if (headers.get(secondKey).contains("%s")) {
                    headers.put(secondKey, String.format(headers.get(secondKey), input));
                }
                return setApiRequestContext().get(endURl, RequestOptions.create().setQueryParam(firstKey, headers.get(firstKey))
                        .setQueryParam(secondKey, headers.get(secondKey)));

            case 3:
                System.out.println("Executing 3rd loop");
                headers = getQueryParameter(input);
                firstKey = headers.keySet().stream().findFirst().orElse(null);
                secondKey = headers.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = headers.keySet().stream().skip(2).findFirst().orElse(null);
                System.out.println(firstKey +"="+headers.get(firstKey)+ secondKey +"="+ headers.get(secondKey) + thirdKey +"="+headers.get(thirdKey));

                assert firstKey != null;
                if (headers.get(firstKey).contains("%s")) {
                    headers.put(firstKey, String.format(headers.get(firstKey), input));
                } else {
                    assert secondKey != null;
                    if (headers.get(secondKey).contains("%s")) {
                        headers.put(secondKey, String.format(headers.get(secondKey), input));
                    }
                }
                assert thirdKey != null;
                if (headers.get(thirdKey).contains("%s")) {
                    headers.put(thirdKey, String.format(headers.get(thirdKey), input));
                }
                return setApiRequestContext().get(endURl, RequestOptions.create().setQueryParam(firstKey, headers.get(firstKey))
                        .setQueryParam(secondKey, headers.get(secondKey)).setQueryParam(thirdKey, headers.get(thirdKey)));

            case 4:
                System.out.println("Executing 4th loop");
                headers = getQueryParameter(input);
                firstKey = headers.keySet().stream().findFirst().orElse(null);
                secondKey = headers.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = headers.keySet().stream().skip(2).findFirst().orElse(null);
                fourthKey = headers.keySet().stream().skip(3).findFirst().orElse(null);
                assert firstKey != null;
                if (headers.get(firstKey).contains("%s")) {
                    headers.put(firstKey, String.format(headers.get(firstKey), input));
                } else {
                    assert secondKey != null;
                    if (headers.get(secondKey).contains("%s")) {
                        headers.put(secondKey, String.format(headers.get(secondKey), input));
                    }
                }
                assert thirdKey != null;
                if (headers.get(thirdKey).contains("%s")) {
                    headers.put(thirdKey, String.format(headers.get(thirdKey), input));
                }
                assert fourthKey != null;
                if (headers.get(fourthKey).contains("%s")) {
                    headers.put(fourthKey, String.format(headers.get(fourthKey), input));
                }
                return setApiRequestContext().get(endURl, RequestOptions.create().setQueryParam(firstKey, headers.get(firstKey))
                        .setQueryParam(secondKey, headers.get(secondKey)).
                        setQueryParam(thirdKey, headers.get(thirdKey)).setQueryParam(fourthKey, headers.get(fourthKey)));
        }
        return null;
    }
    public APIResponse getMethodWithoutParam(String endURl) {
        return setApiRequestContext().get(endURl, RequestOptions.create());
        }

    public String getSchema(String pathName) throws IOException {
        return Files.readString(Path.of(TestBase.apiProp.getProperty(pathName)));
    }
    public String updateExistingSchema(String jsonSchema, String path, String updateValue) {
        jsonSchema = jsonSchema.replace(JsonPath.read(jsonSchema, "$" + path), updateValue);

        return jsonSchema;
    }

    public String updateExistingSchemaWithResult(String jsonSchema, String path, APIResponse result, Object updateValue) {
        Object existingValue = JsonPath.read(jsonSchema, "$" + path);
        Object newValue = JsonPath.read(result.text(), "$" + updateValue);

        String existingValueStr = String.valueOf(existingValue);
        String replacementValueStr = String.valueOf(newValue);

        jsonSchema = jsonSchema.replace(existingValueStr, replacementValueStr);

        System.out.println(jsonSchema);
        return jsonSchema;
    }



    public String updateSchema(String jsonSchema, String path, String updateValue) {
        String[] updatedValue = updateValue.split("&");
        String updateVal = "";
        Properties property;
        if (Settings.envName.contains("prod")) {
            property = TestBase.prodProperty;
        } else if (Settings.envName.contains("preprod")) {
            property = TestBase.preprodProperty;

        } else {
            property = TestBase.stagingProperty;
        }
        if (updatedValue.length == 1) {
            System.out.println(" passing from prop file" + TestBase.prop.getProperty(updateValue));
            jsonSchema = jsonSchema.replace(JsonPath.read(jsonSchema, "$" + path), property.getProperty(updateValue));
//            System.out.println(jsonSchema);
        } else {
            for (int i = 0; i < updatedValue.length; i++) {
                updateVal = updateVal + property.getProperty(updatedValue[i]);
                System.out.println(" adding in updated value" + updateVal);
            }
            jsonSchema = jsonSchema.replace(JsonPath.read(jsonSchema, "$" + path), updateVal);
            System.out.println(jsonSchema);
        }
        return jsonSchema;

    }

    public APIResponse postMethod(String endURl, String input, String schema) {
        String[] variables = input.split("&");
        Map<String, String> parameter;
        String firstKey;
        String secondKey;
        String thirdKey;
        String fourthKey;
//    [bot=x1671106954323,subscription=25183]
        switch (variables.length) {
            case 1:
                System.out.println("Executing 1st loop");
                String[] parts = input.split("\\W+");
                return setApiRequestContext().post(endURl, RequestOptions.create().setQueryParam(parts[0], parts[1]).setData(schema));

            case 2:
//    [bot=x1671106954323,subscription=25183]
                System.out.println("Executing 2nd loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                return setApiRequestContext().post(endURl, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).setData(schema));

            case 3:
                System.out.println("Executing 3rd loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                return setApiRequestContext().get(endURl, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).setQueryParam(thirdKey, parameter.get(thirdKey)).setData(schema));

            case 4:
                System.out.println("Executing 4th loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                fourthKey = parameter.keySet().stream().skip(3).findFirst().orElse(null);
                return setApiRequestContext().get(endURl, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).
                        setQueryParam(thirdKey, parameter.get(thirdKey)).setQueryParam(fourthKey, parameter.get(fourthKey)).setData(schema));
        }
        return null;
    }
    public APIResponse postMethodWithoutParam(String endURl, String schema) {
        return setApiRequestContext().post(endURl, RequestOptions.create().setData(schema));

    }

    public APIResponse deleteMethod(String endURl, String queryParam, Object input) {
        String[] variables = queryParam.split("&");
        Map<String, String> parameter;
        String firstKey;
        String secondKey;
        String thirdKey;
        String fourthKey;
        String formattedURL;

        String inputString = input.toString();

// Check if the search string is present in the URL
        if (endURl.contains("%s")) {
// Add your desired formatted string or modification logic here
            formattedURL = String.format(endURl, input);

        } else {
// If the search string is not present, pass the original URL as is
            formattedURL = endURl;
        }

        switch (variables.length) {
            case 1:
                System.out.println("Executing 1st loop");
                String[] parts = queryParam.split("\\W+");
                return setApiRequestContext().delete(formattedURL, RequestOptions.create().setQueryParam(parts[0], parts[1]));

            case 2:
//    [bot=x1671106954323,subscription=25183]
                System.out.println("Executing 2nd loop");
                parameter = getQueryParameter(queryParam);

                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                System.out.println(firstKey + secondKey + inputString);

                if (parameter.get(firstKey).contains("%s")) {
                    parameter.put(firstKey, String.format(parameter.get(firstKey), inputString));
                }
                if (parameter.get(secondKey).contains("%s")) {
                    parameter.put(secondKey, String.format(parameter.get(secondKey), inputString));
                }
                System.out.println(parameter.get(firstKey) + parameter.get(secondKey));
                return setApiRequestContext().delete(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)));

            case 3:
                System.out.println("Executing 3rd loop");
                parameter = getQueryParameter(queryParam);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                assert firstKey != null;
                if (parameter.get(firstKey).contains("%s")) {
                    parameter.put(firstKey, String.format(parameter.get(firstKey), inputString));
                } else {
                    assert secondKey != null;
                    if (parameter.get(secondKey).contains("%s")) {
                        parameter.put(secondKey, String.format(parameter.get(secondKey), inputString));
                    }
                }
                assert thirdKey != null;
                if (parameter.get(thirdKey).contains("%s")) {
                    parameter.put(thirdKey, String.format(parameter.get(thirdKey), inputString));
                }
                return setApiRequestContext().delete(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).setQueryParam(thirdKey, parameter.get(thirdKey)));

            case 4:
                System.out.println("Executing 4th loop");
                parameter = getQueryParameter(queryParam);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                fourthKey = parameter.keySet().stream().skip(3).findFirst().orElse(null);
                assert firstKey != null;
                if (parameter.get(firstKey).contains("%s")) {
                    parameter.put(firstKey, String.format(parameter.get(firstKey), inputString));
                } else {
                    assert secondKey != null;
                    if (parameter.get(secondKey).contains("%s")) {
                        parameter.put(secondKey, String.format(parameter.get(secondKey), inputString));
                    }
                }
                assert thirdKey != null;
                if (parameter.get(thirdKey).contains("%s")) {
                    parameter.put(thirdKey, String.format(parameter.get(thirdKey), inputString));
                }
                assert fourthKey != null;
                if (parameter.get(fourthKey).contains("%s")) {
                    parameter.put(fourthKey, String.format(parameter.get(fourthKey), inputString));
                }
                return setApiRequestContext().delete(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).
                        setQueryParam(thirdKey, parameter.get(thirdKey)).setQueryParam(fourthKey, parameter.get(fourthKey)));
        }
        return null;
    }

    public APIResponse patchMethod(String endURl, String queryParam, Object input, String schema) {
        String[] variables = queryParam.split("&");
        Map<String, String> parameter;
        String firstKey;
        String secondKey;
        String thirdKey;
        String fourthKey;
        String formattedURL;

        String inputString = input.toString();

// Check if the search string is present in the URL
        if (endURl.contains("%s")) {
// Add your desired formatted string or modification logic here
            formattedURL = String.format(endURl, inputString);

        } else {
// If the search string is not present, pass the original URL as is
            formattedURL = endURl;
        }

        switch (variables.length) {
            case 1:
                System.out.println("Executing 1st loop");
                String[] parts = queryParam.split("\\W+");
                return setApiRequestContext().patch(formattedURL, RequestOptions.create().setQueryParam(parts[0], parts[1]).setData(schema));

            case 2:
//    [bot=x1671106954323,subscription=25183]
                System.out.println("Executing 2nd loop");
                parameter = getQueryParameter(queryParam);

                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                System.out.println(firstKey + secondKey + inputString);

                if (parameter.get(firstKey).contains("%s")) {
                    parameter.put(firstKey, String.format(parameter.get(firstKey), inputString));
                }
                if (parameter.get(secondKey).contains("%s")) {
                    parameter.put(secondKey, String.format(parameter.get(secondKey), inputString));
                }
                System.out.println(parameter.get(firstKey) + parameter.get(secondKey));
                return setApiRequestContext().patch(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).setData(schema));

            case 3:
                System.out.println("Executing 3rd loop");
                parameter = getQueryParameter(queryParam);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                assert firstKey != null;
                if (parameter.get(firstKey).contains("%s")) {
                    parameter.put(firstKey, String.format(parameter.get(firstKey), inputString));
                } else {
                    assert secondKey != null;
                    if (parameter.get(secondKey).contains("%s")) {
                        parameter.put(secondKey, String.format(parameter.get(secondKey), inputString));
                    }
                }
                assert thirdKey != null;
                if (parameter.get(thirdKey).contains("%s")) {
                    parameter.put(thirdKey, String.format(parameter.get(thirdKey), inputString));
                }
                return setApiRequestContext().patch(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).setQueryParam(thirdKey, parameter.get(thirdKey)).setData(schema));

            case 4:
                System.out.println("Executing 4th loop");
                parameter = getQueryParameter(queryParam);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                fourthKey = parameter.keySet().stream().skip(3).findFirst().orElse(null);
                assert firstKey != null;
                if (parameter.get(firstKey).contains("%s")) {
                    parameter.put(firstKey, String.format(parameter.get(firstKey), inputString));
                } else {
                    assert secondKey != null;
                    if (parameter.get(secondKey).contains("%s")) {
                        parameter.put(secondKey, String.format(parameter.get(secondKey), inputString));
                    }
                }
                assert thirdKey != null;
                if (parameter.get(thirdKey).contains("%s")) {
                    parameter.put(thirdKey, String.format(parameter.get(thirdKey), inputString));
                }
                assert fourthKey != null;
                if (parameter.get(fourthKey).contains("%s")) {
                    parameter.put(fourthKey, String.format(parameter.get(fourthKey), inputString));
                }
                return setApiRequestContext().patch(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).
                        setQueryParam(thirdKey, parameter.get(thirdKey)).setQueryParam(fourthKey, parameter.get(fourthKey)).setData(schema));
        }
        return null;
    }


    public Number checkGivenValueIsInteger(String input) {
        if (input.matches("\\d+")) {
            return Integer.parseInt(input);

        } else if (input.matches("\\d+\\.\\d+")) {
            return Double.parseDouble(input);
        } else {
            return null;
        }

    }

    //Calculate and return current date and time in UTC format
    public String getCurrentDate() {
        ZonedDateTime currentDateTime = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC);

// Set the time for the start date (18:30:00.000Z)
        ZonedDateTime startDate = currentDateTime.minusDays(1).withHour(18).withMinute(30).withSecond(0).withNano(0);

// Set the time for the end date (18:29:59.999Z)
        ZonedDateTime endDate = startDate.plusDays(1).minusNanos(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return startDate.format(formatter) + "/" + endDate.format(formatter);
    }

    //Calculate and return yesterday's date and time in UTC format
    public String getYesterdayDate() {
        ZonedDateTime currentDateTime = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC);

// Set the time for the start date (18:30:00.000Z)
        ZonedDateTime startDate = currentDateTime.minusDays(2).withHour(18).withMinute(30).withSecond(0).withNano(0);

// Set the time for the end date (18:29:59.999Z)
        ZonedDateTime endDate = startDate.plusDays(1).minusNanos(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return startDate.format(formatter) + "/" + endDate.format(formatter);
    }

    //Calculate and return last 7 days- date and time in UTC format
    public String Last7Days() {
        ZonedDateTime currentDateTime = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC);

// Set the time for the start date (18:30:00.000Z)
        ZonedDateTime startDate = currentDateTime.minusDays(8).withHour(18).withMinute(30).withSecond(0).withNano(0);

// Set the time for the end date (18:29:59.999Z)
        ZonedDateTime endDate = startDate.plusDays(7).minusNanos(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return startDate.format(formatter) + "/" + endDate.format(formatter);
    }

    //Calculate and return last 28 days- date and time in UTC format
    public String Last28Days() {
        ZonedDateTime currentDateTime = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC);

// Set the time for the start date (18:30:00.000Z)
        ZonedDateTime startDate = currentDateTime.minusDays(29).withHour(18).withMinute(30).withSecond(0).withNano(0);

// Set the time for the end date (18:29:59.999Z)
        ZonedDateTime endDate = startDate.plusDays(28).minusNanos(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return startDate.format(formatter) + "/" + endDate.format(formatter);
    }

    //Calculate and return last 90 days- date and time in UTC format
    public String Last90Days() {
        ZonedDateTime currentDateTime = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC);

// Set the time for the start date (18:30:00.000Z)
        ZonedDateTime startDate = currentDateTime.minusDays(91).withHour(18).withMinute(30).withSecond(0).withNano(0);

// Set the time for the end date (18:29:59.999Z)
        ZonedDateTime endDate = startDate.plusDays(90).minusNanos(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return startDate.format(formatter) + "/" + endDate.format(formatter);
    }

    public APIResponse putMethod(String endURl, String input, String schema)
    {
        String[] variables = input.split("&");
        Map<String, String> parameter;
        String firstKey;
        String secondKey;
        String thirdKey;
        String fourthKey;
        String formattedURL;
        if (endURl.contains("%s")) {
            formattedURL = String.format(endURl,input);

        } else {
            formattedURL = endURl;
        }

        switch (variables.length) {
            case 1:
                System.out.println("Executing 1st loop");
                String[] parts = input.split("\\W+");
                return setApiRequestContext().put(formattedURL, RequestOptions.create().setQueryParam(parts[0], parts[1]).setData(schema));

            case 2:
                System.out.println("Executing 2nd loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                return setApiRequestContext().put(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).setData(schema));

            case 3:
                System.out.println("Executing 3rd loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                return setApiRequestContext().put(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).setQueryParam(thirdKey, parameter.get(thirdKey)).setData(schema));

            case 4:
                System.out.println("Executing 4th loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                fourthKey = parameter.keySet().stream().skip(3).findFirst().orElse(null);
                return setApiRequestContext().put(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).
                        setQueryParam(thirdKey, parameter.get(thirdKey)).setQueryParam(fourthKey, parameter.get(fourthKey)).setData(schema));
        }
        return null;
    }
    public APIResponse putMethodWithoutParam(String endURl, String firstJson) {
        return setApiRequestContext().put(endURl, RequestOptions.create().setData(firstJson));
    }

    public Properties getProperty(){
        if (Settings.envName.equalsIgnoreCase("prod")) {
            property = TestBase.prodProperty;
        }
        else if (Settings.envName.equalsIgnoreCase("preprod")) {
            property = TestBase.preprodProperty;
        }
        else {
            property = TestBase.stagingProperty;
        }
        return property;
    }

    public String getQuery(String query) {
        String[] queryParm = query.split("&");
        StringBuilder updateVal = new StringBuilder();
        boolean botIdUpdated = false;

        for (int i = 0; i < queryParm.length; i++) {
            String param = queryParm[i];
            if (param.equalsIgnoreCase("botId")) {
                updateVal.append(Settings.requiredParam);
                botIdUpdated = true;
            } else {
                if (updateVal.length() > 0) {
                    updateVal.append("&");
                }
                updateVal.append(param);
            }
        }
        if (queryParm.length == 1 && !botIdUpdated) {
            return Settings.requiredParam;
        }

        System.out.println("Updating URL: " + updateVal);
        return updateVal.toString();
    }
    public String updateSchemaStartEndDate(String jsonSchema, String path, String path2, String filterBy) {
        String startEndDate = null;
        if (filterBy.equals("Last 7 days"))
            startEndDate = Last7Days();
        if (filterBy.equals("Yesterday"))
            startEndDate = getYesterdayDate();
        if (filterBy.equals("Last 28 days"))
            startEndDate = Last28Days();
        if (filterBy.equals("Last 90 days"))
            startEndDate = Last90Days();
        if (filterBy.equals("Today"))
            startEndDate = getCurrentDate();
        String[] result = startEndDate.split("/");
        String startDate = result[0];
        String endDate = result[1];
        System.out.println("Start Date : " + startDate + " End Date : " + endDate);
        jsonSchema = jsonSchema.replace(JsonPath.read(jsonSchema, "$" + path), startDate);
        jsonSchema = jsonSchema.replace(JsonPath.read(jsonSchema, "$" + path2), endDate);
        System.out.println(jsonSchema);
        return jsonSchema;
    }

    public APIResponse postMethodForDynamicURL(String endURl, String input,String path, String schema) {
        String[] variables = input.split("&");
        Map<String, String> parameter;
        String firstKey;
        String secondKey;
        String thirdKey;
        String fourthKey;
        String formattedURL;
//    [bot=x1671106954323,subscription=25183]

        if (endURl.contains("%s")) {
// Add your desired formatted string or modification logic here
            formattedURL = String.format(endURl,path);
            System.out.println("updated url "+ formattedURL);

        } else {
// If the search string is not present, pass the original URL as is
            formattedURL = endURl;
        }

        switch (variables.length) {
            case 1:
                System.out.println("Executing 1st loop");
                String[] parts = input.split("\\W+");
                return setApiRequestContext().put(formattedURL, RequestOptions.create().setQueryParam(parts[0], parts[1]).setData(schema));

            case 2:
//    [bot=x1671106954323,subscription=25183]
                System.out.println("Executing 2nd loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                return setApiRequestContext().post(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).setData(schema));

            case 3:
                System.out.println("Executing 3rd loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                return setApiRequestContext().get(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).setQueryParam(thirdKey, parameter.get(thirdKey)).setData(schema));

            case 4:
                System.out.println("Executing 4th loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                fourthKey = parameter.keySet().stream().skip(3).findFirst().orElse(null);
                return setApiRequestContext().get(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).
                        setQueryParam(thirdKey, parameter.get(thirdKey)).setQueryParam(fourthKey, parameter.get(fourthKey)).setData(schema));
        }
        return null;
    }

    public APIResponse putMethodForDynamicURL(String endURl, String input,String path, String schema)
    {
        String[] variables = input.split("&");
        Map<String, String> parameter;
        String firstKey;
        String secondKey;
        String thirdKey;
        String fourthKey;
        String formattedURL;
// Check if the search string is present in the URL
        if (endURl.contains("%s")) {
// Add your desired formatted string or modification logic here
            formattedURL = String.format(endURl,path);

        } else {
// If the search string is not present, pass the original URL as is
            formattedURL = endURl;
        }

        switch (variables.length) {
            case 1:
                System.out.println("Executing 1st loop");
                String[] parts = input.split("\\W+");
                return setApiRequestContext().put(formattedURL, RequestOptions.create().setQueryParam(parts[0], parts[1]).setData(schema));

            case 2:
//    [bot=x1671106954323,subscription=25183]
                System.out.println("Executing 2nd loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                return setApiRequestContext().put(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).setData(schema));

            case 3:
                System.out.println("Executing 3rd loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                return setApiRequestContext().put(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).setQueryParam(thirdKey, parameter.get(thirdKey)).setData(schema));

            case 4:
                System.out.println("Executing 4th loop");
                parameter = getQueryParameter(input);
                firstKey = parameter.keySet().stream().findFirst().orElse(null);
                secondKey = parameter.keySet().stream().skip(1).findFirst().orElse(null);
                thirdKey = parameter.keySet().stream().skip(2).findFirst().orElse(null);
                fourthKey = parameter.keySet().stream().skip(3).findFirst().orElse(null);
                return setApiRequestContext().put(formattedURL, RequestOptions.create().setQueryParam(firstKey, parameter.get(firstKey))
                        .setQueryParam(secondKey, parameter.get(secondKey)).
                        setQueryParam(thirdKey, parameter.get(thirdKey)).setQueryParam(fourthKey, parameter.get(fourthKey)).setData(schema));
        }
        return null;
    }



}