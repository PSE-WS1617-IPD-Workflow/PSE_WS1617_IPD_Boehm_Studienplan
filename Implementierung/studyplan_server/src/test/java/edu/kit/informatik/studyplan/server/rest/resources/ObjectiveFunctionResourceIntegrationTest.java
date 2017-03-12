package edu.kit.informatik.studyplan.server.rest.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class ObjectiveFunctionResourceIntegrationTest extends SimpleRestAssuredTestWithAuth {
    @Test
    public void getAllObjectiveFunctionsValidKeys() throws Exception {
        customAuthorize(given()).get("objective-functions").then().assertThat().
                statusCode(200).
                body("functions.every { it.containsKey('id') && it.containsKey('name') }", is(true));
    }

    @Test
    public void getAllObjectiveFunctionsValidIds() throws Exception {
    	customAuthorize(given()).get("objective-functions").then().assertThat().
                statusCode(200).
                body("functions.every { it.id >= 0 }", is(true));
    }

    @Test
    public void getAllObjectiveFunctionsHasElements() throws Exception {
    	customAuthorize(given()).get("objective-functions").then().assertThat()
                .statusCode(200)
                .body("functions.size", greaterThan(0));
    }

}