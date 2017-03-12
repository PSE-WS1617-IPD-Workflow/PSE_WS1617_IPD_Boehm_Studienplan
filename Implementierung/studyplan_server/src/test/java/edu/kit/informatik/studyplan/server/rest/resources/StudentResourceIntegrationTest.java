package edu.kit.informatik.studyplan.server.rest.resources;

import static io.restassured.RestAssured.*;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StudentResourceIntegrationTest extends SimpleRestAssuredTestWithAuth {

	private String accessToken;
	private Map<String, Object> json;
	private Map<String, Object> discipline;
	private Map<String, Object> studyStart;
	private HashMap<String, Object> student;
	
	@Before
	public void setUp() throws Exception {
		String string = given().auth().basic("it_user", "password")
		.config(newConfig().redirect(redirectConfig().followRedirects(false)))
		.queryParam("client_id", "key-26hg02lsa")
		.queryParam("scope", "student")
		.queryParam("state", 42)
		.queryParam("response_type", "token")
		.get("/auth/login").getHeader("Location");
		int start = new URI(string).getFragment().indexOf("=") + 1;
		int end = new URI(string).getFragment().indexOf("&");
		accessToken = "Bearer " + new URI(string).getFragment().substring(start, end);
		
		json = new HashMap<String, Object>();
		discipline = new HashMap<String, Object>();
		studyStart = new HashMap<String, Object>();
		student = new HashMap<String, Object>();
		student.put("discipline", discipline);
		student.put("study-start", studyStart);
		json.put("student", student);
		
	}

	@After
	public void tearDown() throws Exception {
		customAuthorize(given(), accessToken).delete("student").then()
		.assertThat().statusCode(200);
	}

	@Test
	public void standardRequest() throws Exception {
		//check if new user is correctly initialized
		customAuthorize(given(), accessToken).get("student").then()
			.assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("studentresult-schema.json"))
			.body("discipline", nullValue())
			.body("study-start", nullValue())
			.body("passed-modules", nullValue());
		
		//check if setting of discipline and study start works
		discipline.put("id", 1);
		studyStart.put("semester-type", "WT");
		studyStart.put("year", 2015);
		
		customAuthorize(given(), accessToken)
			.contentType("application/json")
			.body(json)
			.put("/student").then()
			.assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("studentresult-schema.json"))
			.body("student.discipline.id", equalTo(1))
			.body("student.study-start.semester-type", equalTo("WT"))
			.body("student.study-start.year", equalTo(2015));
	}
	
	@Test
	public void invalidDisciplineId() throws Exception {
		//checks if setting an invalid discipline ID works;
		discipline.put("id", 42);
		studyStart.put("semester-type", "WT");
		studyStart.put("year", 2015);
		
		customAuthorize(given(), accessToken)
			.contentType("application/json")
			.body(json)
			.put("/student").then()
				.assertThat().statusCode(404);
	}
	
	@Test
	public void studyStartTooLongAgo() throws Exception {
		//checks if setting a study start to long ago, so that the maximum semester bound is reached is not possible
		discipline.put("id", 1);
		studyStart.put("semester-type", "WT");
		studyStart.put("year", 1700);
		
		customAuthorize(given(), accessToken)
			.contentType("application/json")
			.body(json)
			.put("/student").then()
			.assertThat().statusCode(422);
	}
	
	@Test
	public void studyStartInFuture() throws Exception {
		
		//checks if study start in future fails
		discipline.put("id", 1);
		studyStart.put("semester-type", "WT");
		studyStart.put("year", LocalDate.now().getYear() + 1);
		
		customAuthorize(given(), accessToken)
			.contentType("application/json")
			.body(json)
			.put("/student").then()
			.assertThat().statusCode(422);
	}
	
	@Test
	public void addPassedModules() {
		
		discipline.put("id", 1);
		studyStart.put("semester-type", "WT");
		studyStart.put("year", 2015);
		LinkedList<Object> passedModules = new LinkedList<Object>();
		HashMap<String, Object> module = new HashMap<String, Object>();
		module.put("id", "M-INFO-101170");
		module.put("semester", "1");
		passedModules.add(module);
		student.put("passed-modules", passedModules);
		
		customAuthorize(given(), accessToken)
			.contentType("application/json")
			.body(json)
			.put("/student").then()
			.assertThat().statusCode(200)
			.body("student.passed-modules.first().id", equalTo("M-INFO-101170"));
	}
	
	
	@Test
	public void addPassedModulesInvalidModule() {
		discipline.put("id", 1);
		studyStart.put("semester-type", "WT");
		studyStart.put("year", 2015);
		LinkedList<Object> passedModules = new LinkedList<Object>();
		HashMap<String, Object> module = new HashMap<String, Object>();
		module.put("id", "M-INFO-478");
		module.put("semester", "1");
		passedModules.add(module);
		student.put("passed-modules", passedModules);
		
		customAuthorize(given(), accessToken)
			.contentType("application/json")
			.body(json)
			.put("/student").then()
			.assertThat().statusCode(404);
	}
	
	@Test
	public void addPassedModulesInvalidSemester() {
		discipline.put("id", 1);
		studyStart.put("semester-type", "WT");
		studyStart.put("year", 2015);
		LinkedList<Object> passedModules = new LinkedList<Object>();
		HashMap<String, Object> module = new HashMap<String, Object>();
		module.put("id", "M-INFO-101170");
		module.put("semester", "100");
		passedModules.add(module);
		student.put("passed-modules", passedModules);
		
		customAuthorize(given(), accessToken)
			.contentType("application/json")
			.body(json)
			.put("/student").then()
			.assertThat().statusCode(422);
	}
	
	@Test
	public void notAuthorized() {
		get("/student").then().assertThat().statusCode(401);
		put("/student").then().assertThat().statusCode(401);
		delete("/student").then().assertThat().statusCode(401);
	}
	
	


}
