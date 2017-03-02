package edu.kit.informatik.studyplan.server.rest.resources;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Ignore;

import java.net.ConnectException;

import static org.junit.Assume.assumeNoException;

@Ignore
public class SimpleRestAssuredTest {
    @BeforeClass
    public static void setUp() throws Exception {
        RestAssured.port = 9999;
        RestAssured.basePath = "/studyplan/rest/";
        RestAssured.baseURI = "http://localhost";
        Exception exception = null;
        try {
            RestAssured.get("/").thenReturn();
        } catch (Exception connectException) {
            if (connectException.getClass() == ConnectException.class)
                exception = connectException;
        }
        assumeNoException("INFORMATION: Skipping test (connection to server refused)", exception);
        //If connecting succeeds, proceed
    }
}
