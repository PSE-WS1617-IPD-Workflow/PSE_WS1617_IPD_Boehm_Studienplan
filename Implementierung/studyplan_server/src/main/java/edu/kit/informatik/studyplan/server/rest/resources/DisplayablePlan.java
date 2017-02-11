package edu.kit.informatik.studyplan.server.rest.resources;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

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
	
	public double getTotalCredits() {
		return semesters.stream().mapToDouble(semester -> semester.getTotalCredits()).sum();
	}
	
	public String formatCreditPoints(double creditPoints) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
		DecimalFormat decimalFormat = new DecimalFormat("#.#", symbols);
		return decimalFormat.format(creditPoints);
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
