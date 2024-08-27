package base;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import config.APIFrameworkConfig;
import config.Settings;

import java.util.Collections;

public class FrameworkInitialize {
    public Page InitializePlaywright(){
        TestBase browserInitialize = new TestBase();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();

        launchOptions.headless= Settings.Headless;
        launchOptions.slowMo = Settings.count;
        if (!Settings.BrowserName.equalsIgnoreCase("webkit")){
            launchOptions.args= Collections.singletonList(Settings.argValues);
        }
        FrameworkConfig.Browser = browserInitialize.GetBrowser(Settings.BrowserName,launchOptions);











        APIFrameworkConfig.context=context;



        return browserInitialize.GetPage(context);


    }
}
