package hooks;


import base.FrameworkConfig;
import base.FrameworkInitalize;
import base.FrameworkNormalBrowser;
import com.microsoft.playwright.Tracing;
import config.ConfigReader;
import io.cucumber.java.*;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class Hooks {


//    @BeforeAll
//    public static void beforeAll(){
//        File myObj = new File("./traces");
//        try {
//            FileUtils.deleteDirectory(myObj);
//        }
//        catch (java.io.IOException e){
//            e.printStackTrace();
//        }
//        myObj.mkdir();
//
//    }


    @Before(order = 1)
    public void beforeRun(@NotNull Scenario scenario) {
//it will execute before scenario run
        ArrayList<String> scenarioTags = (ArrayList<String>) scenario.getSourceTagNames();
        System.out.println(scenarioTags);
        Allure.addAttachment("Environment", "Environment Details:\nEnvironment:"+ System.getProperty("env"));
        if (scenarioTags.contains("@admin")) {
            ConfigReader.PopulateSettings();
            FrameworkConfig.LocalPage = new FrameworkInitalize().InitializePlaywright();
        } else if (scenarioTags.contains("@normalWindow")) {
            ConfigReader.PopulateSettings();
            FrameworkConfig.LocalPage = new FrameworkNormalBrowser().GetBrowser();
        } else {
            System.out.println("PLease add admin tag before executing!!!");

        }

    }


    /**
     * Runs after each UI Test whether pass or fail
     * */

    @After
    public void cleanUp(@NotNull Scenario scenario)  {
        ArrayList<String> scenarioTags = (ArrayList<String>) scenario.getSourceTagNames();
        System.out.println("After Tc"+scenarioTags);
        if (scenarioTags.contains("@admin")) {
//            if(scenario.isFailed()) {
//
//                String screenshotName = scenario.getName().replaceAll("\\s", "_");
//                if (FrameworkConfig.LocalPage2 != null) {
//                    FrameworkConfig.LocalPage2.context().tracing().stop(new Tracing.StopOptions().setPath(Paths.get("traces/" + screenshotName + "1" + ".zip")));
//                }
//                FrameworkConfig.LocalPage.context().tracing().stop(new Tracing.StopOptions().setPath(Paths.get("traces/" + screenshotName + ".zip")));
//            }
            FrameworkConfig.LocalPage.close();
            FrameworkConfig.Browser.close();
            FrameworkConfig.context.close();
            FrameworkConfig.Playwright.close();
            if (FrameworkConfig.LocalPage2 != null) {
                FrameworkConfig.LocalPage2.close();
            }
        } else if (scenarioTags.contains("@normalWindow")) {
            FrameworkConfig.context.close();
            FrameworkConfig.LocalPage.close();
            FrameworkConfig.Playwright.close();
        }


    }
//    @AfterAll
//    public static void renameAllureResults() throws IOException {
//        Path source = Paths.get("target/allure-results");
//        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//        Path target = Paths.get("target/allure-results_" + timestamp);
//        Files.move(source, target);
//    }
}
