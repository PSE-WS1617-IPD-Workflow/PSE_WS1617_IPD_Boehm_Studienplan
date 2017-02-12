package edu.kit.informatik.studyplan.server.model.moduledata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class modeling a module description
 * @author NiklasUhl
 * @version 1.0
 */
@Entity
@Table(name = "module_description")
public class ModuleDescription {
	
	@Id
	@Column(name = "description_id")
	@JsonIgnore
	private int descriptionId;

	@Column(name = "description_text")
	private String descriptionText;
	
	@Column(name = "lecturer")
	private String lecturer;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private ModuleType moduleType;

	/**
	 * 
	 * @return returns the unique description id
	 */
	public int getDescriptionId() {
		return descriptionId;
	}

	/**
	 * 
	 * @return returns the description text
	 */
	public String getDescriptionText() {
		return descriptionText;
	}

	/**
	 * 
	 * @return returns the lecturer
	 */
	public String getLecturer() {
		return descriptionText;
	}

	/**
	 * 
	 * @return returns the module type
	 * @see ModuleType
	 */
	public ModuleType getModuleType() {
		return moduleType;
	}

	/**
	 * @param descriptionId the descriptionId to set
	 */
	void setDescriptionId(int descriptionId) {
		this.descriptionId = descriptionId;
	}

	/**
	 * @param descriptionText the descriptionText to set
	 */
	void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	/**
	 * @param lecturer the lecturer to set
	 */
	void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	/**
	 * @param moduleType the moduleType to set
	 */
	void setModuleType(ModuleType moduleType) {
		this.moduleType = moduleType;
	}
};
