import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Listeners;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/sample.feature:27"},
        plugin = {"json:target/cucumber-parallel/1.json", "html:target/cucumber-parallel/1"},
        monochrome = false,
        glue = {"com.pmt.health.steps"})
@Listeners({com.pmt.health.utilities.Listener.class})
public class Sample01IT extends AbstractTestNGCucumberTests {
}
