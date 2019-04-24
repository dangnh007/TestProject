import org.testng.annotations.*;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        strict = true,
        features = {"/Users/grasbergerm/Projects/PMTAutomationFramework/src/test/resources/features/login.feature:8"},
        plugin = {"json:/Users/grasbergerm/Projects/PMTAutomationFramework/target/cucumber-parallel/1.json", "html:/Users/grasbergerm/Projects/PMTAutomationFramework/target/cucumber-parallel/1"},
        monochrome = false,
        glue = {"com.pmt.health.steps"})
public class Login01IT extends AbstractTestNGCucumberTests {
}