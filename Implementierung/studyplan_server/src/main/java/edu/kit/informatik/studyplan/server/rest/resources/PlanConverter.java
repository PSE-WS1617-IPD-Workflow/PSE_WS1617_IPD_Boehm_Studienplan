package edu.kit.informatik.studyplan.server.rest.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

public class PlanConverter {
	
	private StringWriter writer;

	public PlanConverter(Plan plan) throws IOException {
		VelocityEngine engine = new VelocityEngine();
		Properties properties = new Properties();
		InputStream cssStream;
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("velocity.properties"));
			cssStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("report.css");
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		}
		int n = cssStream.available();
		byte[] bytes = new byte[n];
		cssStream.read(bytes, 0, n);
		String css = new String(bytes, StandardCharsets.UTF_8);
		engine.init(properties);
		Template template = engine.getTemplate("report.vm");
		DisplayablePlan displayablePlan = new DisplayablePlan(plan);
		VelocityContext context = new VelocityContext();
		context.put("css", css);
		context.put("plan", displayablePlan);
		writer = new StringWriter();
        template.merge(context, writer);
	}
	
	public StringWriter getWriter() {
		return writer;
	}
}
