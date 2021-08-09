package WSR.WSRTesting;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features= "src/test/java/features",
glue= "stepsDefinition")
public class TestRunner extends AbstractTestNGCucumberTests{
	
}
