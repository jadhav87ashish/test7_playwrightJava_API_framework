package utility;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

import java.util.Map;

public class APISetup {






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



        }



        return null;
    }

    private APIRequestContext setAPIRequestContext() {
        System.out.println(setBaseURL);
        return FrameworkConfig.playwright.request().newContext(new APIRequest().NewContextOptions())
                .setBaseURL(setBaseURL()).setExtraHTTPHeaders(setHeaders()).setTimeout(30000));

    }


    public String getQuery(String param) {



        return param;
    }
}
