package hooks;

import base.FrameworkConfig;
import base.FrameworkInitialize;
import base.FrameworkNormalBrowser;
import config.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Hooks {
//    @Before (order = 1)
    @Before (order = 1)
    public void beforeRun(@NotNull Scenario scenario){
        ArrayList<String> scenarioTags = (ArrayList<String>) scenario.getSourceTagNames();
        System.out.println(scenarioTags);
        Allure.addAttachment("Environment","Environment Details:\nEnvironment:"+System.getProperty("env"));
        if (scenarioTags.contains("@admin")){
            ConfigReader.PopulateSettings();
            FrameworkConfig.localPage = new FrameworkInitialize().InitializePlaywright();
        }
        else if (scenarioTags.contains("@normalWindow")){
            ConfigReader.PopulateSettings();
            FrameworkConfig.LocalPage = new FrameworkNormalBrowser().GetBrowser();
        }
        else {
            System.out.println("Please add admin tag");
        }
    }
    @After
    public void cleanUp(@NotNull Scenario scenario){
        ArrayList<String> scenarioTags = (ArrayList<String>) scenario.getSourceTagNames();
        System.out.println("After Test: "+scenarioTags);
        if (scenarioTags.contains("@admin")){
            FrameworkConfig.LocalPage.close();
            FrameworkConfig.Browser.close();
            FrameworkConfig.context.close();
            FrameworkConfig.Playwright.close();
            if (FrameworkConfig.LocalPage2 !=null){
                FrameworkConfig.LocalPage2.close();
            }
        } else if (scenarioTags.contains("@normalWindow")) {
            FrameworkConfig.context.close();
            FrameworkConfig.LocalPage.close();
            FrameworkConfig.Playwright.close();

        }
    }


}
