package edu.kit.informatik.studyplan.server.rest.resources.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;

/**
 * DTO for ModuleConstraints.
 */
public class ConstraintDto {
	
	@JsonProperty
	String type;
	@JsonProperty
	ConstraintModuleDto first;
	@JsonProperty
	ConstraintModuleDto second;

	/**
	 * Creates a new ConstraintDto from a ModuleConstraint
	 * @param constraint the ModuleConstraint
     */
	public ConstraintDto (ModuleConstraint constraint) {
		this.type = constraint.getConstraintType().getDescription();
		this.first = new ConstraintModuleDto(constraint.getFirstModule());
		this.second = new ConstraintModuleDto(constraint.getSecondModule());
	}

	/**
	 * Creates a new ConstraintDto from a Plan-related ModuleConstraint
	 * @param constraint the ModuleConstraint
	 * @param plan the plan as ModulePreference source
     */
	public ConstraintDto (ModuleConstraint constraint, Plan plan) {
		this.type = constraint.getConstraintType().getDescription();
		this.first = new ConstraintModuleDto(constraint.getFirstModule(), plan);
		this.second = new ConstraintModuleDto(constraint.getSecondModule(), plan);
	}

	/**
	 * DTO for Modules inside a Constraint
     */
	public class ConstraintModuleDto {
		
		@JsonProperty
		String id;
		
		@JsonProperty
		String name;
		
		@JsonProperty("cycle-type")
		CycleType cycleType;
		
		@JsonProperty("creditpoints")
		double creditPoints;
		
		@JsonProperty
		PreferenceType preference;

		/**
		 * Creates a ConstraintModuleDto from a Module
		 * @param module the Module
         */
		public ConstraintModuleDto (Module module) {
			this.id = module.getIdentifier();
			this.name = module.getName();
			this.cycleType = module.getCycleType();
			this.creditPoints = module.getCreditPoints();
		}

		/**
		 * Creates a ConstraintModuleDto with preference from a plan
		 * @param module the module
         * @param plan the plan as preference source
         */
		public ConstraintModuleDto (Module module, Plan plan) {
			this.id = module.getIdentifier();
			this.name = module.getName();
			this.cycleType = module.getCycleType();
			this.creditPoints = module.getCreditPoints();
			this.preference = plan.getPreferenceForModule(module);
		}
	}
}
