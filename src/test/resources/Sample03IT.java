import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Listeners;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/sample.feature:79"},
        plugin = {"json:target/cucumber-parallel/3.json", "html:target/cucumber-parallel/3"},
        monochrome = false,
        glue = {"com.pmt.health.steps"})
@Listeners({com.pmt.health.utilities.Listener.class})
public class SampleMultipleOutline2IT extends AbstractTestNGCucumberTests {
}
