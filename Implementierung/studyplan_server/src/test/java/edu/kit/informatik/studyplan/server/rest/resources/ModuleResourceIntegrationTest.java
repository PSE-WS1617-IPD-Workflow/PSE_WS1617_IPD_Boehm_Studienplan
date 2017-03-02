package edu.kit.informatik.studyplan.server.rest.resources;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ModuleResourceIntegrationTest extends SimpleRestAssuredTestWithAuth {

	@Test
	public void getModulesValidJson() {
		customAuthorize(given()).get("modules/M-INFO-101176").then().assertThat().
        statusCode(200).body(matchesJsonSchemaInClasspath("moduleresult-schema.json"));
	}
	
	@Test
	public void allPropertiesCorrectlySerialized() {
		customAuthorize(given()).get("modules/M-INFO-101176").then().assertThat().
        statusCode(200)
        	.body(matchesJsonSchemaInClasspath("moduleresult-schema.json"))
        	.body("module.id", equalTo("M-INFO-101176"))
        	.body("module.name", equalTo("PSE"))
        	.body("module.categories.size()", equalTo(1))
        	.body("module.categories.first().id", equalTo(55))
        	.body("module.categories.first().name", equalTo("Praktische Informatik"))
        	.body("module.cycle-type", equalTo("both"))
        	.body("module.creditpoints", equalTo(7.0f))
        	.body("module.lecturer", equalTo("Snelting"))
        	.body("module.compulsory", equalTo(true))
        	.body("module.description", equalTo("Praxis der Softwareentwicklung"))
        	.body("module.constraints.size()", equalTo(1))
        	.body("module.constraints.first().type", equalTo("semester_link"))
        	.body("module.constraints.first().first.id", equalTo("M-INFO-101176"))
        	.body("module.constraints.first().second.id", equalTo("M-INFO-101225"));
	}
	
	@Test
	public void multipleConstraintsSerialized() {
		customAuthorize(given()).get("modules/M-MATH-101313").then().assertThat()
		.statusCode(200)
			.body(matchesJsonSchemaInClasspath("moduleresult-schema.json"))
			.body("module.constraints.size()", equalTo(2));
	}
	
	@Test
	public void nonCompulsoryAndNoDescription() {
		customAuthorize(given()).get("modules/M-INFO-101249").then().assertThat()
		.statusCode(200)
			.body(matchesJsonSchemaInClasspath("moduleresult-schema.json"))
			.body("module.compulsory", equalTo(false))
			.body("module.description", equalTo(""))
			.body("module.lecturer", equalTo(""))
			.body("module.cycle-type", equalTo("WT"))
			.body("module.creditpoints", equalTo(5f));
	}
	
	@Test
	public void invalidModuleId() {
		customAuthorize(given()).get("modules/M-INFO-478").then().assertThat()
		.statusCode(404);
	}
}
