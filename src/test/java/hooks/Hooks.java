package hooks;

import base.FrameworkConfig;
import base.FrameworkInitialize;
import config.ConfigReader;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Hooks {
    @Before(order = 1)
    public void beforeRun(@NotNull Scenario scenario){
        ArrayList<String> scenarioTags = (ArrayList<String>) scenario.getSourceTagNames();
        System.out.println(scenarioTags);
        Allure.addAttachment("Environment","Environment Details:\nEnvironment:"+System.getProperty("env"));
        if (scenarioTags.contains("@admin")){
            ConfigReader.PopulateSettings();
            FrameworkConfig.localPage = new FrameworkInitialize().InitializePlaywright();
        }
//        else if (scenarioTags.contains("@normalWindow")){
//            ConfigReader.PopulateSettings();
//            FrameworkConfig.LocalPage = new FrameworkNormalBrowser().GetBrowser();
//        }
        else {
            System.out.println("Please add admin tag");
        }

    }
}
