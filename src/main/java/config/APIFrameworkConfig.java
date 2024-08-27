package config;


import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:${user.dir}/src/main/resources/apiProperties/preProd.properties",
        "file:${user.dir}/src/main/resources/apiProperties/prod.properties",
        "file:${user.dir}/src/main/resources/apiProperties/staging.properties"
})

public interface APIFrameworkConfig extends Config{
    @Key("${env}.url")
    String url();
}
