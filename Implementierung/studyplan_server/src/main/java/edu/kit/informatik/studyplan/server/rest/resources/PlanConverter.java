package edu.kit.informatik.studyplan.server.rest.resources;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

public class PlanConverter {
	
	private StringWriter writer;

	public PlanConverter(Plan plan){
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
        template.merge( context, writer );
	}
	
	public StringWriter getWriter() {
		return writer;
	}

	
	
	
	
	public class DisplayablePlan {
		
		private List<Semester> semesters;
		private String name;
		
		public DisplayablePlan(Plan plan) {
			this.name = plan.getName();
			semesters = plan.getAllModuleEntries().stream()
			.collect(Collectors.groupingBy(ModuleEntry::getSemester))
			.entrySet().stream()
			.map(entry -> new Semester(entry.getKey(), entry.getValue()))
			.collect(Collectors.toList());
		}
		
		public List<Semester> getSemesters() {
			return semesters.stream().sorted().collect(Collectors.toList());
		}
		
		public String getName() {
			return name;
		}
		
		
	}
	
	public class Semester implements Comparable<Semester>{
		private int number;
		private List<ModuleEntry> moduleEntries;
		
		public Semester(int number, List<ModuleEntry> entries) {
			this.number = number;
			this.moduleEntries = entries;
		}
		public int getNumber() {
			return number;
		}
		
		public List<Module> getModules() {
			return moduleEntries.stream().map(entry -> entry.getModule()).collect(Collectors.toList());
		}
		
		public double getTotalCredits() {
			return getModules().stream().mapToDouble(modue -> modue.getCreditPoints()).sum();
		}
		@Override
		public int compareTo(Semester o) {
			return this.number - o.number;
		}
	}
}
