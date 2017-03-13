package edu.kit.informatik.studyplan.server.rest.resources;

import io.restassured.specification.RequestSpecification;

class SimpleRestAssuredTestWithAuth extends SimpleRestAssuredTest {
    static RequestSpecification customAuthorize(RequestSpecification given) {
        return customAuthorize(given, "Bearer admin123");
    }

    static RequestSpecification customAuthorize(RequestSpecification given, String accessToken) {
        return given
                .header("Authorization", accessToken)
                .header("Origin", "*");
    }
}
