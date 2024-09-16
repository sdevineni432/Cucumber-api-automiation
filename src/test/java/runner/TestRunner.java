package runner;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features ="src/test/resources/Features",
		glue="stepdefinitions",
		plugin = {"pretty", "html:test-output/cucumber-reports.html", "json:test-output/cucumber.json"},
	    monochrome = true
		)


public class TestRunner {

}