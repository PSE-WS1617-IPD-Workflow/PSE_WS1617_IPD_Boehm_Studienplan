package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.rest.MyObjectMapperProvider;

import javax.persistence.*;

/**
 * Class modeling a module constraint
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
	@Transient
	@JsonSerialize(using = MyObjectMapperProvider.CustomSerializerModule.ModuleConstraintTypeSerializer.class)
	@JsonProperty("type")
	private ModuleConstraintType constraintType;

	@ManyToOne
	@JoinColumn(name = "module1")
	@JsonProperty("first")
	private Module firstModule;

	@ManyToOne
	@JoinColumn(name = "module2")
	@JsonProperty("second")
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
	 * 
	 * @return returns the constraint type
	 * @see ModuleConstraintType
	 */
	public ModuleConstraintType getConstraintType() {
		return constraintType;
	}

	/**
	 * @param constraintType the constraintType to set
	 */
	void setConstraintType(ModuleConstraintType constraintType) {
		this.constraintType = constraintType;
	}

	/**
	 * @param firstModule the firstModule to set
	 */
	void setFirstModule(Module firstModule) {
		this.firstModule = firstModule;
	}

	/**
	 * @param secondModule the secondModule to set
	 */
	void setSecondModule(Module secondModule) {
		this.secondModule = secondModule;
	}
}