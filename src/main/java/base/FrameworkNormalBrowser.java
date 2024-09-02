package base;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import config.Settings;

import java.nio.file.Paths;

public class FrameworkNormalBrowser {
    public  Page GetBrowser() {
        new TestBase();
        String path = System.getProperty("user.dir");
        FrameworkConfig.Playwright = Playwright.create();
        FrameworkConfig.context =  FrameworkConfig.Playwright.chromium().launchPersistentContext(Paths.get(path + "/Library/Application Support/Google/Chrome/Profile 1")
                , new BrowserType.LaunchPersistentContextOptions().setHeadless(Settings.Headless));
        return  FrameworkConfig.context.newPage();
    }
}