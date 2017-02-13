package edu.kit.informatik.studyplan.server.rest.resources.json;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;

public class ModuleDto {
	
	
	@JsonProperty
	String id;
	@JsonProperty
	String name;
	List<CategoryDto> categories;
	@JsonProperty
	CycleType cycleType;
	@JsonProperty("creditpoins")
	double creditPoints;
	@JsonProperty
	String lecturer;
	@JsonProperty
	PreferenceType preference;
	@JsonProperty
	String description;
	@JsonProperty
	List<ConstraintDto> constraints;
	
	
	public ModuleDto (Module module) {
		this.id = module.getIdentifier();
		this.name = module.getName();
		this.categories = module.getCategories().stream()
				.map(CategoryDto::new).collect(Collectors.toList());
		this.creditPoints = module.getCreditPoints();
		String lecturerName = module.getModuleDescription().getLecturer();
		this.lecturer = lecturerName != null ? lecturerName : "";
		String descriptionText = module.getModuleDescription().getDescriptionText();
		this.description = descriptionText != null ? descriptionText : "";
		this.constraints = module.getConstraints().stream()
				.map(ConstraintDto::new).collect(Collectors.toList());
		
	}
	
	public ModuleDto (Module module, Plan plan) {
		this(module);
		this.preference = plan.getPreferenceForModule(module);
		this.constraints = module.getConstraints().stream()
				.map(constraint -> new ConstraintDto(constraint, plan)).collect(Collectors.toList());
	}
}
