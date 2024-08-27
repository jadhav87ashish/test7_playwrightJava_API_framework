package base;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class TestBase {
    public static Properties prop;
    public static Properties apiProp;
    public static Properties resourceApiConfig;
    public static Properties preProdProperty;
    public static Properties stagingProperty;
    public static Properties prodProperty;

    public TestBase(){
        try{
            prop=new Properties();
            apiProp=new Properties();
            resourceApiConfig=new Properties();
            preProdProperty=new Properties();
            stagingProperty=new Properties();
            prodProperty=new Properties();

            InputStream ip = Files.newInputStream(Paths.get("src/main/java/config/config.properties"));
            InputStream apiIP = Files.newInputStream(Paths.get("src/main/java/config/apisConfig.properties"));
            InputStream prodPro = Files.newInputStream(Paths.get("src/main/java/apiProperties/prodPro.properties"));
            InputStream preProdPro = Files.newInputStream(Paths.get("src/main/java/apiProperties/preProdPro.properties"));
            InputStream stagingPro = Files.newInputStream(Paths.get("src/main/java/apiProperties/stagingPro.properties"));


            prop.load(ip);
            apiProp.load(apiIP);
            preProdProperty.load(preProdPro);
            stagingProperty.load(stagingPro);
            prodProperty.load(prodPro);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }




}
