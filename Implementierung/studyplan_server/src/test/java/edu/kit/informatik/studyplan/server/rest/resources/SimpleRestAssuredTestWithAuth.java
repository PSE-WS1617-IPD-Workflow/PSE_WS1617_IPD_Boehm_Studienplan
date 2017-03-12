package edu.kit.informatik.studyplan.server.rest.resources;

import io.restassured.specification.RequestSpecification;

class SimpleRestAssuredTestWithAuth extends SimpleRestAssuredTest {
    static RequestSpecification customAuthorize(RequestSpecification given) {
        return given
                .header("Authorization", "Bearer admin123")
                .header("Origin", "*");

//                  .auth().preemptive().basic("max_mustermann", "123");
                    //doesn't work!?
    }
    static RequestSpecification customAuthorize(RequestSpecification given, String accessToken) {
        return given
                .header("Authorization", accessToken)
                .header("Origin", "*");

//                  .auth().preemptive().basic("max_mustermann", "123");
                    //doesn't work!?
    }
}
