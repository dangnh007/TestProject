import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Listeners;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/sample.feature:67"},
        plugin = {"json:target/cucumber-parallel/2.json", "html:target/cucumber-parallel/2"},
        monochrome = false,
        glue = {"com.pmt.health.steps"})
@Listeners({com.pmt.health.utilities.Listener.class})
public class SampleMultipleOutline1IT extends AbstractTestNGCucumberTests {
}
