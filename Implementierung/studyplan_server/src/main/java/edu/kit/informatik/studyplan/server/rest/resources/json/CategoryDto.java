package edu.kit.informatik.studyplan.server.rest.resources.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.moduledata.Category;

public class CategoryDto {
	
	@JsonProperty
	int id;
	
	@JsonProperty
	String name;

	public CategoryDto (Category category) {
		this.id = category.getCategoryId();
		this.name = category.getName();
	}
}
