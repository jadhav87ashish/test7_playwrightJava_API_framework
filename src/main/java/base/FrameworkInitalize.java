package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import config.Settings;

import java.util.Collections;


public class FrameworkInitalize {


    // Playwright object initialised here
    public Page InitializePlaywright() {

    //Browser initiated
        TestBase browserInitalize = new TestBase();

    //Launch Options configuration
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.headless = Settings.Headless;
        launchOptions.slowMo = Settings.count;
        if (!Settings.BrowserName.equalsIgnoreCase("webkit")) {
            launchOptions.args = Collections.singletonList(Settings.argValue);
        }
    //Get Browser
        FrameworkConfig.Browser = browserInitalize.GetBrowser(Settings.BrowserName, launchOptions);


    //Create browserContext
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
        contextOptions.locale = Settings.Locale;
        contextOptions.setGeolocation(17.530525, 75.448299).
                setPermissions(Collections.singletonList("geolocation"));
    //contextOptions.setViewportSize(1360,768);

        BrowserContext context = browserInitalize.GetBrowserContext(FrameworkConfig.Browser, contextOptions);
        context.setDefaultTimeout(15000);


        if (Settings.BrowserName.equalsIgnoreCase("firefox")) {
            context.grantPermissions(Collections.singletonList("notifications"));
        }


        FrameworkConfig.context = context;
// context.tracing().start(new Tracing.StartOptions().setSnapshots(true).setScreenshots(true).setSources(false));

//Get Page
        return browserInitalize.GetPage(context);

    }
}