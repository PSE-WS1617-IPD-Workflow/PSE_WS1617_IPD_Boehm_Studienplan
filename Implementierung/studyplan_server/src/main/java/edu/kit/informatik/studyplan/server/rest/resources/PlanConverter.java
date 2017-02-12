package edu.kit.informatik.studyplan.server.rest.resources;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

public class PlanConverter {
	
	private StringWriter writer;

	public PlanConverter(Plan plan) {
		VelocityEngine engine = new VelocityEngine();
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("velocity.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		engine.init(properties);
		Template template = engine.getTemplate("report.vm");
		DisplayablePlan displayablePlan = new DisplayablePlan(plan);
		VelocityContext context = new VelocityContext();
		context.put("plan", displayablePlan);
		writer = new StringWriter();
        template.merge(context, writer);
	}
	
	public StringWriter getWriter() {
		return writer;
	}
}
