package utility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private int retries;

    public RetryAnalyzer(){
        int defaultRetry = 2;
        String retriesEnv = System.getenv("PLAYWRIGHT_RETRY");
        if (retriesEnv!=null){
            try {
                retries = Integer.parseInt(retriesEnv);
            } catch (NumberFormatException e){
                System.out.println("Invalid number format: "+retriesEnv+".Using default retries: "+ defaultRetry);
                retries = defaultRetry;
            }
        } else {
            System.out.println("PLAYWRIGHT_RETRY value is not set. Using default retries: "+defaultRetry);
            retries = defaultRetry;
        }
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount<retries){
            retryCount++;
            return true;
        }
        return false;
    }
}
