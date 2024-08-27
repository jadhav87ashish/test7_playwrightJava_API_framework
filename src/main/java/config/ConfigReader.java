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

        Settings.envName = System.getProperty("env");
        Settings.URL = config.url();

    }
}
