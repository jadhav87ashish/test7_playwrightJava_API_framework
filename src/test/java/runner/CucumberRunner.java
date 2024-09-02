package runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;



@CucumberOptions(tags = ("@Acronymslist"),features = "src/test/resources/features/",
        glue = {"steps", "hooks"},
        plugin = { "pretty:target/cucumber-pretty.txt","usage:target/cucumber-usage.json","json:target/cucumber-reports/Cucumber.json",
                "junit:target/cucumber-reports/Cucumber.xml",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},

        monochrome = true)

public class CucumberRunner extends AbstractTestNGCucumberTests {


}