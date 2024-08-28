package base;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import config.Settings;

public class FrameworkNormalBrowser {

    public Page GetBrowser() {
        new TestBase();
        String path = System.getProperty("user.dir");
        FrameworkConfig.Playwright = Playwright.create();
        FrameworkConfig.context = (BrowserContext) FrameworkConfig.Playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(Settings.Headless));
        return FrameworkConfig.context.newPage();

    }
}
