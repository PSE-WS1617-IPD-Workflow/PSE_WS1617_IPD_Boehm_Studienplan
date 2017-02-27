package edu.kit.informatik.studyplan.server.rest.resources;

import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class DisciplinesResourceIntegrationTest extends SimpleRestAssuredTest {
    @Test
    public void getAllDisciplinesValidKeys() throws Exception {
        get("disciplines").then().assertThat().
                statusCode(200).
                body("disciplines.every { it.containsKey('id') && it.containsKey('name') }", is(true));
    }

    @Test
    public void getAllDisciplinesNoTooChatty() throws Exception {
        get("disciplines").then().assertThat().
                statusCode(200).
                body("disciplines.any { it.any { prop -> prop.key != 'id' && prop.key != 'name' } }", is(false));
                //Groovy lacks a `none` method, so we're checking against `!any()`
    }

    @Test
    public void getAllDisciplinesValidIds() throws Exception {
        get("disciplines").then().assertThat().
                statusCode(200).
                body("disciplines.every { it.id >= 0 }", is(true));
    }

    @Test
    public void getAllDisciplinesHasElements() throws Exception {
        get("disciplines").then().assertThat()
                .statusCode(200)
                .body("disciplines.size", greaterThan(0));
    }
}