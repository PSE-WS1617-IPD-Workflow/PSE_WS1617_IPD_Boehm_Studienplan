package edu.kit.informatik.studyplan.server.rest.resources;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * Class for constructing a plan grouped by semester
 * @author NiklasUhl
 *
 */
public class DisplayablePlan {
	
	private List<Semester> semesters;
	private String name;
	
	/**
	 * Constructs an instance from a plan
	 * @param plan the plan
	 */
	public DisplayablePlan(Plan plan) {
		this.name = plan.getName();
		semesters = plan.getAllModuleEntries().stream()
		.collect(Collectors.groupingBy(ModuleEntry::getSemester))
		.entrySet().stream()
		.map(entry -> new Semester(entry.getKey(), entry.getValue()))
		.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return returns the semesters of this plan
	 */
	public List<Semester> getSemesters() {
		return semesters.stream().sorted().collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return the plan name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return the total credit points
	 */
	public double getTotalCredits() {
		return semesters.stream().mapToDouble(semester -> semester.getTotalCredits()).sum();
	}
	
	/**
	 * Formats a given credit point number to one decimal point
	 * @param creditPoints the credit points
	 * @return the formated value as String
	 */
	public String formatCreditPoints(double creditPoints) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
		DecimalFormat decimalFormat = new DecimalFormat("#.#", symbols);
		return decimalFormat.format(creditPoints);
	}
	
	/**
	 * Class representing a Semester containing modules
	 * @author NiklasUhl
	 *
	 */
	public class Semester implements Comparable<Semester> {
		private int number;
		private List<ModuleEntry> moduleEntries;
		
		/**
		 * Creates a new Semester with the given number and entries
		 * @param number the semester number
		 * @param entries the semester's module entries
		 */
		public Semester(int number, List<ModuleEntry> entries) {
			this.number = number;
			this.moduleEntries = entries;
		}
		
		/**
		 * 
		 * @return the semester number
		 */
		public int getNumber() {
			return number;
		}
		
		/**
		 * 
		 * @return all modules of this semester
		 */
		public List<Module> getModules() {
			return moduleEntries.stream().map(entry -> entry.getModule()).collect(Collectors.toList());
		}
		
		/**
		 * @return the total credit points of this semester
		 */
		public double getTotalCredits() {
			return getModules().stream().mapToDouble(modue -> modue.getCreditPoints()).sum();
		}
		@Override
		public int compareTo(Semester o) {
			return this.number - o.number;
		}
	}
}
