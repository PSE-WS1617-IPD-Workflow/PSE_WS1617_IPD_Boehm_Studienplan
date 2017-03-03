package edu.kit.informatik.studyplan.server.rest.resources.json;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;
import edu.kit.informatik.studyplan.server.rest.MyObjectMapperProvider;

/**
 * DTO for Module class
 */
public class ModuleDto {
	
	
	@JsonProperty
	String id;
	@JsonProperty
	String name;
	@JsonProperty
	List<CategoryDto> categories;
	@JsonProperty("cycle-type")
	CycleType cycleType;
	@JsonProperty("creditpoints")
	double creditPoints;
	@JsonProperty
	boolean compulsory;
	@JsonProperty
	String lecturer;
	@JsonProperty
	@JsonSerialize(using = MyObjectMapperProvider.CustomSerializerModule.PreferenceTypeSerializer.class)
	@JsonDeserialize(using = MyObjectMapperProvider.CustomSerializerModule.PreferenceTypeDeserializer.class)
	PreferenceType preference;
	@JsonProperty
	String description;
	@JsonProperty
	List<ConstraintDto> constraints;

	/**
	 * Creates a ModuleDto from a module.
	 * @param module module
     */
	public ModuleDto(Module module) {
		this.id = module.getIdentifier();
		this.name = module.getName();
		this.categories = module.getCategories().stream()
				.map(CategoryDto::new).collect(Collectors.toList());
		this.cycleType = module.getCycleType();
		this.creditPoints = module.getCreditPoints();
		this.compulsory = module.isCompulsory();
		String lecturerName = module.getModuleDescription().getLecturer();
		this.lecturer = lecturerName != null ? lecturerName : "";
		String descriptionText = module.getModuleDescription().getDescriptionText();
		this.description = descriptionText != null ? descriptionText : "";
		this.constraints = module.getConstraints().stream()
				.map(ConstraintDto::new).collect(Collectors.toList());
		
	}

	/**
	 * Creates a plan-related ModuleDto (containing module preferences).
	 * @param module the module
	 * @param plan the plan as preference source
     */
	public ModuleDto(Module module, Plan plan) {
		this(module);
		this.preference = plan.getPreferenceForModule(module);
		this.constraints = module.getConstraints().stream()
				.map(constraint -> new ConstraintDto(constraint, plan)).collect(Collectors.toList());
	}
}
