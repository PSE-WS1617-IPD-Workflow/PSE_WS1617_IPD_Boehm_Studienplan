package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.rest.MyObjectMapperProvider;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

/**
 * Class modeling a module constraint
 * 
 * @author NiklasUhl
 * @version 1.0
 */
@Entity
@Table(name = "module_constraint")
public class ModuleConstraint {

	@Id
	@Column(name = "constraint_id")
	@JsonIgnore
	private int constraintId;

	@ManyToOne
	@JoinColumn(name = "type_id")
	@JsonSerialize(using = MyObjectMapperProvider.CustomSerializerModule.ModuleConstraintTypeSerializer.class)
	@JsonProperty("type")
	private ModuleConstraintType constraintType;

	@ManyToOne
	@JoinColumn(name = "module1")
	@JsonIgnore
	private Module firstModule;

	@ManyToOne
	@JoinColumn(name = "module2")
	@JsonIgnore
	private Module secondModule;

	/**
	 * 
	 * @return returns the first module of the relation
	 */
	public Module getFirstModule() {
		return firstModule;
	}

	/**
	 * 
	 * @return returns the second module of the relation
	 */
	public Module getSecondModule() {
		return secondModule;
	}

	/**
	 * Only being called by Jackson and/or REST handlers.
	 * 
	 * @return Serialization getter for first module.
	 */
	@JsonProperty("first")
	public Map<String, String> getJsonFirstModule() {
		return SimpleJsonResponse.build("id", firstModule.getIdentifier());
	}

	/**
	 * Only being called by Jackson and/or REST handlers.
	 * 
	 * @return Serialization getter for second module.
	 */
	@JsonProperty("second")
	public Map<String, String> getJsonSecondModule() {
		return SimpleJsonResponse.build("id", secondModule.getIdentifier());
	}

	/**
	 * 
	 * @return returns the constraint type
	 * @see ModuleConstraintType
	 */
	public ModuleConstraintType getConstraintType() {
		return constraintType;
	}

	/**
	 * @param constraintType
	 *            the constraintType to set
	 */
	public void setConstraintType(ModuleConstraintType constraintType) {
		this.constraintType = constraintType;
	}

	/**
	 * @param firstModule
	 *            the firstModule to set
	 */
	public void setFirstModule(Module firstModule) {
		this.firstModule = firstModule;
	}

	/**
	 * @param secondModule
	 *            the secondModule to set
	 */
	public void setSecondModule(Module secondModule) {
		this.secondModule = secondModule;
	}
}