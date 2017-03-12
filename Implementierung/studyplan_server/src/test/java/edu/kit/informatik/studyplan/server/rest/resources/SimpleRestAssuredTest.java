package edu.kit.informatik.studyplan.server.rest.resources;

import static org.junit.Assume.assumeNoException;

import java.net.ConnectException;

import org.junit.BeforeClass;
import org.junit.Ignore;

import io.restassured.RestAssured;

@Ignore
public class SimpleRestAssuredTest {
   
	@BeforeClass
    public static void setServerConnection() throws Exception {
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
