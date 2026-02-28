package config;

import org.aeonbits.owner.Config;

import java.util.List;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",
        "system:properties",
        "file:${user.dir}/src/main/java/config/config.properties",
        "file:${user.dir}/src/main/resources/apiProperties/prod-config.properties",
        "file:${user.dir}/src/main/resources/apiProperties/preprod-config.properties",
        "file:${user.dir}/src/main/resources/apiProperties/staging-config.properties",
        "file:${user.dir}/src/main/resources/apiProperties/booking-config.properties",

})
public interface FrameworkConfig extends Config {


    @Key("${env}.url")
    String url();

    @Key("${env}.requiredParam")
    String requiredParam();
}