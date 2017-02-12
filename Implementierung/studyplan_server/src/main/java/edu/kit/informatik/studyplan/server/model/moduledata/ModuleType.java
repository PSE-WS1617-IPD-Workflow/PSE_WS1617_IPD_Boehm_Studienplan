package edu.kit.informatik.studyplan.server.model.moduledata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class modeling a module type (e.g. lecture, seminar,...)
 * @author NiklasUhl
 * @version 1.0
 */
@Entity
@Table(name = "module_type")
public class ModuleType {
	
	@Id
	@Column(name = "type_id")
	private int typeId;
	
	@Column(name = "name")
	private String name;

	/**
	 * 
	 * @return returns the unique type id
	 */
	public int getTypeId() {
		return typeId;
	}

	/**
	 * 
	 * @return returns the type's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param typeId the typeId to set
	 */
	void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	/**
	 * @param name the name to set
	 */
	void setName(String name) {
		this.name = name;
	}
};
