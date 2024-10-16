package config;


import org.aeonbits.owner.ConfigFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);

    public static void PopulateSettings() {
        ConfigReader reader = new ConfigReader();
        try {
            reader.ReadProperty();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loading the browser config property in runtime(before hook) or we can call by class object.methodName
    private void ReadProperty() throws IOException {
//Create Property Object
        Properties p = new Properties();
        InputStream inputStream = new FileInputStream("src/main/java/config/BrowserConfig.properties");
        p.load(inputStream);

        Settings.Headless = Boolean.parseBoolean(p.getProperty("Headless_status"));
        System.out.println(" HeadLess " + Settings.Headless);
        Settings.Locale = p.getProperty("Locale");
        Settings.argValue = p.getProperty("argValue");
        Settings.count = Double.parseDouble(p.getProperty("count"));
        Settings.envName = System.getProperty("env");
        System.out.println(Settings.envName);

        String time = System.getProperty("time");
        if (time == null) {
            Settings.timeOut = Integer.parseInt(p.getProperty("DefaultTimeOut"));
        } else {
            Settings.timeOut = Integer.parseInt(System.getProperty("time")); //mvn clean test "-Dcucumber.filter.tags=@getAPI7" -Denv=prod -Dtime=30000

        }





        String browser = System.getProperty("environment");
        if (browser == null) {
            Settings.BrowserName = p.getProperty("BrowserName");
        } else {
            Settings.BrowserName = browser;
        }
        Settings.URL = config.url();
        Settings.requiredParam = config.requiredParam();
    }
}