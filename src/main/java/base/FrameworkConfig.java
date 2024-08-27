package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.aeonbits.owner.Config;

public class FrameworkConfig {
    public static Page LocalPage;
    public static Page LocalPage2;

    public static Playwright playwright;

    public static com.microsoft.playwright.Browser Browser;


    public static BrowserContext context;
}
