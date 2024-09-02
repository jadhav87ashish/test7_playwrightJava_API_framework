package base;

import com.microsoft.playwright.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;


public class TestBase {
    public static Properties prop;
    public static Properties apiProp;
    public static Properties resourceApiConfig;
    public static Properties preprodProperty;
    public static Properties prodProperty;
    public static Properties stagingProperty;


    // Loading the config property in runtime(inheritance) or we can call by className.property object.
    public TestBase() {
        try {
            prop = new Properties();
            apiProp = new Properties();
            resourceApiConfig = new Properties();
            preprodProperty = new Properties();
            prodProperty = new Properties();
            stagingProperty = new Properties();


            InputStream ip = Files.newInputStream(Paths.get("src/main/java/config/config.properties"));
            InputStream apiIP = Files.newInputStream(Paths.get("src/main/java/config/apisConfig.properties"));
            InputStream preprodPro = Files.newInputStream(Paths.get("src/main/resources/apiProperties/preprod-config.properties"));
            InputStream prodPro = Files.newInputStream(Paths.get("src/main/resources/apiProperties/prod-config.properties"));
            InputStream stagingPro = Files.newInputStream(Paths.get("src/main/resources/apiProperties/staging-config.properties"));


            prop.load(ip);
            apiProp.load(apiIP);
            preprodProperty.load(preprodPro);
            prodProperty.load(prodPro);
            stagingProperty.load(stagingPro);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // To trigger the playwright - Getting  browser details
    public Browser GetBrowser(String browserName, BrowserType.LaunchOptions launchOptions) {
        FrameworkConfig.Playwright = Playwright.create();
        BrowserType browserType = null;

        if (browserName.equalsIgnoreCase("chromium"))
            browserType = FrameworkConfig.Playwright.chromium();
        if (browserName.equalsIgnoreCase("firefox"))
            browserType = FrameworkConfig.Playwright.firefox();
        if (browserName.equalsIgnoreCase("webkit")) {
            browserType = FrameworkConfig.Playwright.webkit();
        }
        assert browserType != null;
        return browserType.launch(launchOptions);
    }

    // To trigger the playwright - Getting  BrowserContext details
    public BrowserContext GetBrowserContext(Browser browser, Browser.NewContextOptions newContextOptions) {

        return browser.newContext(newContextOptions);
    }

    // To trigger the playwright - Getting  page object
    public Page GetPage(BrowserContext browserContext) {
        return browserContext.newPage();
    }


}