package edu.kit.informatik.studyplan.server.rest.resources;

import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class ObjectiveFunctionResourceIntegrationTest extends SimpleRestAssuredTest {
    @Test
    public void getAllObjectiveFunctionsValidKeys() throws Exception {
        get("objective-functions").then().assertThat().
                statusCode(200).
                body("functions.every { it.containsKey('id') && it.containsKey('name') }", is(true));
    }

    @Test
    public void getAllObjectiveFunctionsValidIds() throws Exception {
        get("objective-functions").then().assertThat().
                statusCode(200).
                body("functions.every { it.id >= 0 }", is(true));
    }

    @Test
    public void getAllObjectiveFunctionsHasElements() throws Exception {
        get("objective-functions").then().assertThat()
                .statusCode(200)
                .body("functions.size", greaterThan(0));
    }

}