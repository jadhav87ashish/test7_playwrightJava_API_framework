package config;

import base.FrameworkConfig;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    APIFrameworkConfig config = ConfigFactory.create(APIFrameworkConfig.class);
    public static void PopulateSettings(){
        ConfigReader reader = new ConfigReader();
        try{
            reader.ReadProperty();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void ReadProperty() throws IOException {
        Properties p =new Properties();
        InputStream inputStream = new FileInputStream("src/main/java/config/BrowserConfig.properties");
        p.load(inputStream);
        Settings.Headless = Boolean.parseBoolean(p.getProperty("Headless_status"));
        System.out.println("Headless: "+Settings.Headless);
        Settings.Locale = p.getProperty("argValue");
        Settings.argValue = p.getProperty("argValue");
        Settings.count = Double.parseDouble(p.getProperty("count"));
        Settings.envName = System.getProperty("env");
        System.out.println("Environment: "+ Settings.envName);
        String browser = System.getProperty("environment");
        if (browser==null){
            Settings.BrowserName = p.getProperty("BrowserName");
        } else{
            Settings.BrowserName = browser;
        }
        Settings.URL = config.url();

    }
}
