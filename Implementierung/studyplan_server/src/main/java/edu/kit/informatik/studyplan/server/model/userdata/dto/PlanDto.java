package edu.kit.informatik.studyplan.server.model.userdata.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;
import edu.kit.informatik.studyplan.server.model.userdata.VerificationState;

public class PlanDto {
	
	@JsonProperty
	String id;
	
	@JsonProperty
	String name;
	
	@JsonProperty
	VerificationState status;
	
	@JsonProperty("creditpoints-sum")
	double creditPoints;
	
	@JsonProperty
	List<ModuleDto> modules;
	
	public PlanDto(Plan plan) {
		this.id = plan.getIdentifier();
		this.name = plan.getName();
		this.status = plan.getVerificationState();
		this.creditPoints = plan.getCreditPoints();
		modules = plan.getModuleEntries().stream()
			.map(entry -> new ModuleDto(entry, plan.getPreferenceForModule(entry.getModule())))
			.collect(Collectors.toList());
	}
	
	
	public class ModuleDto {
		
		@JsonProperty
		String id;
		
		@JsonProperty
		String name;
		
		@JsonProperty
		int semester;
		
		@JsonProperty("creditpoints")
		double creditPoints;
		
		@JsonProperty
		String lecturer;
		
		@JsonProperty
		PreferenceType preference;
		
		public ModuleDto (ModuleEntry entry, PreferenceType preference) {
			this.id = entry.getModule().getIdentifier();
			this.name = entry.getModule().getName();
			this.semester = entry.getSemester();
			this.creditPoints = entry.getModule().getCreditPoints();
			if (entry.getModule().getModuleDescription() != null) {
				this.lecturer = entry.getModule().getModuleDescription().getLecturer();
			}
			this.preference = preference;
		}
	}
	
}
