package edu.kit.informatik.studyplan.server.rest.resources;

import org.junit.Ignore;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@Ignore  //Auth doesn't work yet
public class FilterResourceTest extends SimpleRestAssuredTestWithAuth {
    @Test
    public void getFiltersValidKeys() throws Exception {
        customAuthorize(given()).get("filters").then().assertThat().
                statusCode(200).
                body("filters.every { it.containsKey('id') && it.containsKey('name') " +
                        "&& it.containsKey('uri-name') && it.containsKey('default-value') " +
                        "&& it.containsKey('tooltip') && it.containsKey('specification') }", is(true));
    }

    @Test
    public void getFiltersValidFilterTypes() throws Exception {
        customAuthorize(given()).get("filters").then().assertThat().
                statusCode(200).
                body("filters.every { it.specification.type == 'list' || it.specification.type == 'range' " +
                        " || it.specification.type == 'contains' }", is(true));
    }

    @Test
    public void getFiltersValidDefaultValues() throws Exception {
        customAuthorize(given()).get("filters").then().assertThat().
                statusCode(200).
                body("filters.every { " +
                        "(it.specification.type == 'list' && it['default-value'] == 0) || " +
                        "(it.specification.type == 'contains' && it['default-value'] == '') || " +
                        "(it.specification.type == 'range' && it['default-value'].min <= it['default-value'].max)" +
                        " }", is(true));
    }

    @Test
    public void getFiltersValidSpecifications() throws Exception {
        customAuthorize(given()).get("filters").then().assertThat().
                statusCode(200).
                body("filters.every { " +
                        "(it.specification.type == 'contains' && it.specification.every { it.key == 'type' }) || " +
                        "(it.specification.type == 'range' && it.specification.min <= it.specification.max) || " +
                        "(it.specification.type == 'list' && it.specification.items.every { " +
                                                                " it.id >= 0 && it.containsKey('text') " +
                                                            "}) " +
                        " }", is(true));
    }

    @Test
    public void getFiltersHasElements() throws Exception {
        customAuthorize(given()).get("filters").then().assertThat()
                .statusCode(200)
                .body("filters.size", greaterThan(0));
    }

}