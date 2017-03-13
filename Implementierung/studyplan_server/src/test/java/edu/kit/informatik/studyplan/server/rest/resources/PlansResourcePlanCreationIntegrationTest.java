package edu.kit.informatik.studyplan.server.rest.resources;

import static io.restassured.RestAssured.*;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.restassured.http.ContentType;

public class PlansResourcePlanCreationIntegrationTest extends SimpleRestAssuredTestWithAuth {

	private String accessToken;

	@Before
	public void setUp() throws URISyntaxException {
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
				
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> discipline = new HashMap<String, Object>();
		Map<String, Object> studyStart = new HashMap<String, Object>();
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("discipline", discipline);
		student.put("study-start", studyStart);
		json.put("student", student);
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
	
	@After
	public void tearDown() throws Exception {
		customAuthorize(given(), accessToken).delete("student").then()
		.assertThat().statusCode(200);
	}

	@Test
	public void noPlansAfterLogin() throws Exception {
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(0));
	}
	
	@Test
	public void postPlan() throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> plan = new HashMap<String, Object>();
		plan.put("name", "Mein Plan");
		json.put("plan", plan);
		String id = customAuthorize(given(), accessToken)
			.body(json)
			.contentType(ContentType.JSON)
			.post("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plan.id", not(nullValue()))
			.body("plan.name", equalTo("Mein Plan"))
			.extract().jsonPath().getString("plan.id");
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(1))
			.body("plans.first().id", equalTo(id))
			.body("plans.first().name", equalTo("Mein Plan"));
	}
	
	@Test
	public void duplicateName() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> plan = new HashMap<String, Object>();
		plan.put("name", "Mein Plan");
		json.put("plan", plan);
		String id = customAuthorize(given(), accessToken)
			.body(json)
			.contentType(ContentType.JSON)
			.post("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plan.id", not(nullValue()))
			.body("plan.name", equalTo("Mein Plan"))
			.extract().jsonPath().getString("plan.id");
		customAuthorize(given(), accessToken)
			.contentType(ContentType.JSON)
			.body(json)
			.post("/plans").then().assertThat().statusCode(422);
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(1))
			.body("plans.first().id", equalTo(id))
			.body("plans.first().name", equalTo("Mein Plan"));
	}
	
	@Test
	public void emptyName() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> plan = new HashMap<String, Object>();
		plan.put("name", "");
		json.put("plan", plan);
		customAuthorize(given(), accessToken)
			.body(json)
			.contentType(ContentType.JSON)
			.post("/plans").then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(0));
	}
	
	@Test
	public void noNameAttribute() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> plan = new HashMap<String, Object>();
		json.put("plan", plan);
		customAuthorize(given(), accessToken)
			.body(json)
			.contentType(ContentType.JSON)
			.post("/plans").then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(0));
	}
	
	@Test
	public void noPlanAttribute() {
		Map<String, Object> json = new HashMap<String, Object>();
		customAuthorize(given(), accessToken)
			.body(json)
			.contentType(ContentType.JSON)
			.post("/plans").then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(0));
	}
	
	@Test
	public void nameTooLong() {
		String goodName = StringUtils.repeat("a", 100);
		String badName = StringUtils.repeat("a", 101);
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> plan = new HashMap<String, Object>();
		plan.put("name", goodName);
		json.put("plan", plan);
		String id = customAuthorize(given(), accessToken)
				.body(json)
				.contentType(ContentType.JSON)
				.post("/plans").then().assertThat().statusCode(200)
				.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
				.body("plan.id", not(nullValue()))
				.body("plan.name", equalTo(goodName))
				.extract().jsonPath().getString("plan.id");
		plan.put("name", badName);
		customAuthorize(given(), accessToken)
			.body(json)
			.contentType(ContentType.JSON)
			.post("/plans").then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(1))
			.body("plans.first().id", equalTo(id))
			.body("plans.first().name", equalTo(goodName));
	}
	
	@Test
	public void unauthorized() {
		get("/plans").then().assertThat().statusCode(401);
		given().contentType(ContentType.JSON).post("/plans").then().assertThat().statusCode(401);
	}
	
	


}
