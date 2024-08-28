package utility;

import base.FrameworkConfig;
import base.TestBase;
import com.google.common.base.CaseFormat;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import config.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class APISetup {
    Properties property;

    public APIResponse getMethod(String endurl, String input){
        String[] variables = input.split("&");
        Map<String,String> headers;
        String firstKey;
        String secondKey;
        String thirdKey;
        String fourthKey;
        String formattedURL;
        if (endurl.contains("%s")){
            formattedURL=String.format(endurl,input);
        } else {
            formattedURL = endurl;
        }
        switch (variables.length){
            case 1:
                System.out.println("Single Param");
                String[] parts = input.split("\\W+");
                return setAPIRequestContext().get(formattedURL, RequestOptions.create().setQueryParam(parts[0],parts[1]));

            case 2:
                {

                }

        }
        return null;
    }

    private APIRequestContext setAPIRequestContext() {
        System.out.println(setBaseURL());
        return FrameworkConfig.Playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(setBaseURL()).setExtraHTTPHeaders(setHeaders()).setTimeout(30000));

    }

    private Map<String, String> setHeaders() {
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        return headers;
    }

    public String setBaseURL(){
        String baseURL;
        if (Settings.envName.equalsIgnoreCase("prod")||
                Settings.envName.equalsIgnoreCase("preProd")||
                Settings.envName.equalsIgnoreCase("staging") ){
            baseURL=Settings.URL;
        }
        else {
            String url = Settings.URL;
            baseURL=url;
        }
        return baseURL;
    }


    public String getQuery(String param) {



        return param;
    }
    public Properties getProperty(){
        if (Settings.envName.contains("preProd")){
            property= TestBase.preProdProperty;
        } else
        if (Settings.envName.contains("staging")){
            property=TestBase.stagingProperty;
        }
        else {
            property=TestBase.prodProperty;
        }
        return property;
    }


}
