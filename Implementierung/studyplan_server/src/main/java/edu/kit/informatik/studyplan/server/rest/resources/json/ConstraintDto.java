package edu.kit.informatik.studyplan.server.rest.resources.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;

public class ConstraintDto {
	
	@JsonProperty
	String type;
	@JsonProperty
	ConstraintModuleDto first;
	@JsonProperty
	ConstraintModuleDto second;
	
	public ConstraintDto (ModuleConstraint constraint) {
		this.type = constraint.getConstraintType().getDescription();
		this.first = new ConstraintModuleDto(constraint.getFirstModule());
		this.second = new ConstraintModuleDto(constraint.getSecondModule());
	}
	
	public ConstraintDto (ModuleConstraint constraint, Plan plan) {
		this.type = constraint.getConstraintType().getDescription();
		this.first = new ConstraintModuleDto(constraint.getFirstModule(), plan);
		this.second = new ConstraintModuleDto(constraint.getSecondModule(), plan);
	}
	
	public class ConstraintModuleDto {
		
		@JsonProperty
		String id;
		
		@JsonProperty
		String name;
		
		@JsonProperty("cycletype")
		CycleType cycleType;
		
		@JsonProperty("creditpoints")
		double creditPoints;
		
		@JsonProperty
		PreferenceType preference;
		
		public ConstraintModuleDto (Module module) {
			this.id = module.getIdentifier();
			this.name = module.getName();
			this.cycleType = module.getCycleType();
			this.creditPoints = module.getCreditPoints();
		}
		
		public ConstraintModuleDto (Module module, Plan plan) {
			this.id = module.getIdentifier();
			this.name = module.getName();
			this.cycleType = module.getCycleType();
			this.creditPoints = module.getCreditPoints();
			this.preference = plan.getPreferenceForModule(module);
		}
	}
}
