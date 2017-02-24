package edu.kit.informatik.studyplan.server.rest.resources;

import org.junit.Ignore;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@Ignore  //Auth doesn't work yet
public class FieldsResourceTest extends SimpleRestAssuredTestWithAuth {
    @Test
    public void getChoosableFieldsValidKeys() throws Exception {
        customAuthorize(given()).get("fields").then().assertThat().
                statusCode(200).
                body("fields.every { it.containsKey('id') && it.containsKey('name') " +
                        "&& it.containsKey('min-ects') && it.containsKey('categories') }", is(true));
    }

    @Test
    public void getChoosableFieldsValidCategories() throws Exception {
        customAuthorize(given()).get("fields").then().assertThat().
                statusCode(200).
                body("fields.every { it.categories.every { it.containsKey('id') " +
                        "&& it.containsKey('name') && it.id >= 0 } }", is(true));
    }

    @Test
    public void getChoosableFieldsValidIdsAndMinEcts() throws Exception {
        customAuthorize(given()).get("fields").then().assertThat().
                statusCode(200).
                body("fields.every { it.id >= 0 && it['min-ects'] >= 0.0 }", is(true));
    }

    @Test
    public void getChoosableFieldsHasElements() throws Exception {
        customAuthorize(given()).get("fields").then().assertThat()
                .statusCode(200)
                .body("fields.size", greaterThan(0));
    }

}