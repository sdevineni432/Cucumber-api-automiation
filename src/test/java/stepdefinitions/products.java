package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

public class products {
	String baseUri = "https://fakestoreapi.com/";
	RequestSpecification _REQ_SPEC;
	Response _RESP;
	ValidatableResponse _VALIDATABLE_RESP;
	Scenario scn;
	
	@Before
	public void BeforeHook(Scenario s) {
		this.scn = s;}
	
	@Given("when i hit the url of get products api endpoint")
	public void when_i_hit_the_url_of_get_products_api_endpoint() {
		_REQ_SPEC=given().baseUri(baseUri)
		.pathParam("mypath", "products");
	}
	    
	@When("i pass the url of products in the request")
	public void i_pass_the_url_of_products_in_the_request() {
		_RESP = _REQ_SPEC.when().get("/{mypath}");
		System.out.println("Response returned as:" + _RESP.toString());
	    
	}
	@Then("I validate the response code as {int}")
	public void i_validate_the_response_code_as(Integer int1) {
		_VALIDATABLE_RESP = _RESP.then();
		_VALIDATABLE_RESP.statusCode(int1);
	   
	}
	
	@Then("the response time should be less than {int} milliseconds")
    public void the_response_time_should_be_less_than(Integer maxResponseTimeMs) {
        // Validate that the response time is less than the specified milliseconds
        long responseTime = _RESP.time();  // Get the response time in milliseconds
        scn.log("Response time: " + responseTime + " milliseconds");
        
        if (responseTime > maxResponseTimeMs) {
            throw new AssertionError("Expected response time to be less than " + maxResponseTimeMs + " ms, but was " + responseTime + " ms.");
        }
    }

    // Performance testing using JUnit assertion (optional if you prefer using built-in validation)
    @Then("the API should respond within {int} milliseconds")
    public void api_performance_check(Integer maxResponseTimeMs) {
        // JUnit assertTimeout checks the response time constraint
        assertTimeout(Duration.ofMillis(maxResponseTimeMs), () -> {
            _RESP = _REQ_SPEC.when().get("/{mypath}");
        });
        scn.log("API responded within the expected " + maxResponseTimeMs + " milliseconds.");
    }
}