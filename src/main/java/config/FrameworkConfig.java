package config;


import org.aeonbits.owner.Config;
import org.checkerframework.checker.units.qual.K;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:${user.dir}/src/main/resources/apiProperties/preProd.properties",
        "file:${user.dir}/src/main/resources/apiProperties/prod.properties",
        "file:${user.dir}/src/main/resources/apiProperties/staging.properties"
})

public interface FrameworkConfig extends Config{
    @Key("${env}.URI")
    String URI();
}
