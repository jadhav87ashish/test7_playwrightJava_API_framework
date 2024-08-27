package runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = ("@AcronymList)"),features = "src/test/resources/features",
    glue = {"steps","hooks"},
        plugin = { "pretty:target/cucumber-pretty.txt"},
        monochrome = true)

public class CucumberRunner extends AbstractTestNGCucumberTests {

}
