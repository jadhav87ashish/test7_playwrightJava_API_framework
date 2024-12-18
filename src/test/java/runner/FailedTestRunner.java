package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "@target/rerun.txt",
        glue = {"steps","hooks"},
        plugin = {"pretty", "html:target/rerun-cucumber-reports.html"}
)

public class FailedTestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
