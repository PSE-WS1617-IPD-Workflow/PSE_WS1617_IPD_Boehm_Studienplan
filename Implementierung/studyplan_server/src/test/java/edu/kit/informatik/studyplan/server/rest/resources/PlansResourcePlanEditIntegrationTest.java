package edu.kit.informatik.studyplan.server.rest.resources;

import static io.restassured.RestAssured.given;
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
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.http.ContentType;

public class PlansResourcePlanEditIntegrationTest extends SimpleRestAssuredTestWithAuth {

	private static String accessToken;
	private String id;

	@BeforeClass
	public static void createAccount() throws URISyntaxException {
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
	
	@Before
	public void createPlan() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> plan = new HashMap<String, Object>();
		plan.put("name", "Mein Plan");
		json.put("plan", plan);
		id = customAuthorize(given(), accessToken)
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
	
	@After
	public void deletePlan() {
		customAuthorize(given(), accessToken).pathParam("planId", id)
			.delete("/plans/{planId}")
			.then().assertThat().statusCode(200);
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(0));
	}
	
	@AfterClass
	public static void deleteAccount() throws Exception {
		customAuthorize(given(), accessToken).delete("student").then()
			.assertThat().statusCode(200);
	}

	@Test
	public void unauthorized() {
		given().pathParam("planId", id).get("/plans/{planId}").then().assertThat().statusCode(401);
		given().pathParam("planId", id).contentType(ContentType.JSON)
			.patch("/plans/{planId}").then().assertThat().statusCode(401);
		given().pathParam("planId", id).delete("/plans/{planId}").then().assertThat().statusCode(401);
		given().pathParam("planId", id).pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(401);
		given().pathParam("planId", id).pathParam("moduleId", "M-INFO-101170")
			.delete("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(401);
		given().pathParam("planId", id).pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.put("/plans/{planId}/modules/{moduleId}/preference")
			.then().assertThat().statusCode(401);
	}
	
	@Test
	public void renamePlan() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> plan = new HashMap<String, Object>();
		plan.put("id", id);
		plan.put("name", "Mein Plan2");
		json.put("plan", plan);
		customAuthorize(given(), accessToken).pathParam("planId", id)
			.contentType(ContentType.JSON)
			.body(json)
			.patch("/plans/{planId}")
			.then().assertThat().statusCode(200)
			.body("plan.id", equalTo(id))
			.body("plan.name", equalTo("Mein Plan2"));
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(1))
			.body("plans.first().id", equalTo(id))
			.body("plans.first().name", equalTo("Mein Plan2"));
	}
	
	@Test
	public void emptyName() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> plan = new HashMap<String, Object>();
		plan.put("name", "");
		plan.put("id", id);
		json.put("plan", plan);
		customAuthorize(given(), accessToken).pathParam("planId", id)
			.contentType(ContentType.JSON)
			.body(json)
			.patch("/plans/{planId}")
			.then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(1))
			.body("plans.first().id", equalTo(id))
			.body("plans.first().name", equalTo("Mein Plan"));
	}
	
	@Test
	public void noNameAttribute() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> plan = new HashMap<String, Object>();
		json.put("plan", plan);
		json.put("id", id);
		customAuthorize(given(), accessToken).pathParam("planId", id)
			.contentType(ContentType.JSON)
			.body(json)
			.patch("/plans/{planId}")
			.then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(1))
			.body("plans.first().id", equalTo(id))
			.body("plans.first().name", equalTo("Mein Plan"));
	}
	
	@Test
	public void noPlanAttribute() {
		Map<String, Object> json = new HashMap<String, Object>();
		customAuthorize(given(), accessToken).pathParam("planId", id)
			.contentType(ContentType.JSON)
			.body(json)
			.patch("/plans/{planId}")
			.then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans").then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("plansgetresult-schema.json"))
			.body("plans", hasSize(1))
			.body("plans.first().id", equalTo(id))
			.body("plans.first().name", equalTo("Mein Plan"));
	}
	
	@Test
	public void nameTooLong() {
		String goodName = StringUtils.repeat("a", 100);
		String badName = StringUtils.repeat("a", 101);
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> plan = new HashMap<String, Object>();
		plan.put("name", goodName);
		plan.put("id", id);
		json.put("plan", plan);
		customAuthorize(given(), accessToken).pathParam("planId", id)
			.contentType(ContentType.JSON)
			.body(json)
			.patch("/plans/{planId}")
			.then().assertThat().statusCode(200)
			.body("plan.name", equalTo(goodName))
			.body("plan.id", equalTo(id));
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
	public void invalidPlanId() {
		customAuthorize(given(), accessToken).pathParam("planId", 42)
			.get("/plans/{planId}").then().assertThat().statusCode(404);
		
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> plan = new HashMap<String, Object>();
		plan.put("id", 42);
		plan.put("name", "Mein Plan2");
		json.put("plan", plan);
		customAuthorize(given(), accessToken).pathParam("planId", 42).contentType(ContentType.JSON)
			.body(json)
			.patch("/plans/{planId}").then().assertThat().statusCode(404);
		customAuthorize(given(), accessToken).pathParam("planId", 42)
			.delete("/plans/{planId}").then().assertThat().statusCode(404);
	}
	
	@Test
	public void putModule() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		module.put("semester", 1);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(200)
			.body("module.id", equalTo("M-INFO-101170"))
			.body("module.semester", equalTo(1));
		customAuthorize(given(), accessToken).get("/plans/{planId}", id)
			.then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("planresult-schema.json"))
			.body("plan.modules", hasSize(1))
			.body("plan.modules.first().id", equalTo("M-INFO-101170"))
			.body("plan.modules.first().semester", equalTo(1))
			.body("plan.creditpoints-sum", equalTo(6.0f));
	}
	
	@Test
	public void putModuleWrongCycleType() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		module.put("semester", 2);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans/{planId}", id)
			.then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("planresult-schema.json"))
			.body("plan.modules", hasSize(0));
	}
	
	@Test
	public void putModuleNegativeSemester() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		module.put("semester", -42);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans/{planId}", id)
			.then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("planresult-schema.json"))
			.body("plan.modules", hasSize(0));
	}
	
	@Test
	public void putModuleTooHighSemester() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		module.put("semester", PlansResource.MAX_SEMESTERS + 1);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans/{planId}", id)
			.then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("planresult-schema.json"))
			.body("plan.modules", hasSize(0));
	}
	
	@Test
	public void putModuleWrongId() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101171");
		module.put("semester", 1);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans/{planId}", id)
			.then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("planresult-schema.json"))
			.body("plan.modules", hasSize(0));
	}
	
	@Test
	public void putModuleNoId() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("semester", 1);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans/{planId}", id)
			.then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("planresult-schema.json"))
			.body("plan.modules", hasSize(0));
	}
	
	@Test
	public void putModuleNoSemester() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans/{planId}", id)
			.then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("planresult-schema.json"))
			.body("plan.modules", hasSize(0));
	}
	
	@Test
	public void putModuleEmpty() {
		Map<String, Object> json = new HashMap<String, Object>();
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans/{planId}", id)
			.then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("planresult-schema.json"))
			.body("plan.modules", hasSize(0));
	}
	
	@Test
	public void putModuleInvalidId() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		module.put("id", "42");
		module.put("semester", 1);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "42")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(400);
		customAuthorize(given(), accessToken).get("/plans/{planId}", id)
			.then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("planresult-schema.json"))
			.body("plan.modules", hasSize(0));
	}
	
	@Test
	public void putModuleAgain() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		module.put("semester", 1);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(200)
			.body("module.id", equalTo("M-INFO-101170"))
			.body("module.semester", equalTo(1));
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(200)
			.body("module.id", equalTo("M-INFO-101170"))
			.body("module.semester", equalTo(1));
		customAuthorize(given(), accessToken).get("/plans/{planId}", id)
			.then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("planresult-schema.json"))
			.body("plan.modules", hasSize(1))
			.body("plan.modules.first().id", equalTo("M-INFO-101170"))
			.body("plan.modules.first().semester", equalTo(1))
			.body("plan.creditpoints-sum", equalTo(6.0f));
		
		module.put("semester", 3);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(200)
			.body("module.id", equalTo("M-INFO-101170"))
			.body("module.semester", equalTo(3));
		customAuthorize(given(), accessToken).get("/plans/{planId}", id)
			.then().assertThat().statusCode(200)
			.body(matchesJsonSchemaInClasspath("planresult-schema.json"))
			.body("plan.modules", hasSize(1))
			.body("plan.modules.first().id", equalTo("M-INFO-101170"))
			.body("plan.modules.first().semester", equalTo(3))
			.body("plan.creditpoints-sum", equalTo(6.0f));
	}
	
	@Test
	public void removeModule() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		module.put("semester", 1);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(200);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.delete("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(200);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.get("/plans/{planId}")
			.then().assertThat().statusCode(200)
			.body("plan.modules", hasSize(0));
	}
	
	@Test
	public void removeModuleNotContained() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		module.put("semester", 1);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(200);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101176")
			.delete("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(422);
	}
	
	@Test
	public void removeInvalidModuleId() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		module.put("semester", 1);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(200);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "42")
			.delete("/plans/{planId}/modules/{moduleId}")
			.then().assertThat().statusCode(404);
	}
	
	@Test
	public void putModulePreferencePositive() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		module.put("preference", "positive");
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}/preference")
			.then().assertThat().statusCode(200)
			.body("module.id", equalTo("M-INFO-101170"))
			.body("module.preference", equalTo("positive"));
		customAuthorize(given(), accessToken)
			.get("/plans/{planId}/modules/{moduleId}", id, "M-INFO-101170")
			.then().assertThat().body("module.preference", equalTo("positive"));
	}
	
	@Test
	public void putModulePreferenceNegative() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		module.put("preference", "negative");
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}/preference")
			.then().assertThat().statusCode(200)
			.body("module.id", equalTo("M-INFO-101170"))
			.body("module.preference", equalTo("negative"));
		customAuthorize(given(), accessToken)
			.get("/plans/{planId}/modules/{moduleId}", id, "M-INFO-101170")
			.then().assertThat().body("module.preference", equalTo("negative"));
	}
	
	@Test
	public void removeModulePreference() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101170");
		module.put("preference", "positive");
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}/preference")
			.then().assertThat().statusCode(200);
		module.put("preference", null);
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}/preference")
			.then().assertThat().statusCode(200)
			.body("module.id", equalTo("M-INFO-101170"))
			.body("module.preference", nullValue());
		customAuthorize(given(), accessToken)
			.get("/plans/{planId}/modules/{moduleId}", id, "M-INFO-101170")
			.then().assertThat().body("module.preference", nullValue());
	}
	
	@Test
	public void putModulePreferenceInvalidId() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "42");
		module.put("preference", "positive");
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "42")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}/preference")
			.then().assertThat().statusCode(404);
	}
	
	@Test
	public void putModulePreferenceNoId() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("preference", "positive");
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}/preference")
			.then().assertThat().statusCode(400);
	}
	
	@Test
	public void putModulePreferenceIdsNotMatching() {
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> module = new HashMap<String, Object>();
		json.put("module", module);
		module.put("id", "M-INFO-101171");
		module.put("preference", "positive");
		customAuthorize(given(), accessToken)
			.pathParam("planId", id)
			.pathParam("moduleId", "M-INFO-101170")
			.contentType(ContentType.JSON)
			.body(json)
			.put("/plans/{planId}/modules/{moduleId}/preference")
			.then().assertThat().statusCode(400);
	}
	
	@Test
	public void getPrintablePlan() {
			String token = accessToken.replace("Bearer ", "");
			given().param("access-token", token)
			.get("/plans/{planId}/pdf", id)
			.then().assertThat().statusCode(200)
			.contentType(ContentType.HTML);
	}
	
	@Test
	public void getPrintablePlanUnauthorized() {
			given().get("/plans/{planId}/pdf", id)
			.then().assertThat().statusCode(401);
	}
	
	@Test
	public void getPrintableInvalidId() {
			given().get("/plans/{planId}/pdf", 42)
			.then().assertThat().statusCode(404);
	}

}
