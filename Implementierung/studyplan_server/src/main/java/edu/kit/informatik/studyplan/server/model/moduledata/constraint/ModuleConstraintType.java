package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;

/**
 * Class modeling the type of a module constraint
 * 
 * @author NiklasUhl
 * @version 1.0
 */
@Entity
@Table(name = "constraint_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "description")
public abstract class ModuleConstraintType {

	@Id
	@Column(name = "type_id")
	private int id;

	@Column(name = "description", insertable = false, updatable = false)
	private String description;

	@Column(name = "formal_description")
	private String formalDescription;

	/**
	 * Checks if two module entries match the conditions of the constraint type
	 * 
	 * @param first
	 *            the first module entry
	 * @param second
	 *            the second moduly entry
	 * @param orientation
	 *            Direction the relation needs to be checked for. <br>
	 *            If Relation -> exists between the modules A and B, the
	 *            relation can be validated with
	 *            <code>isValid(A,B,LEFT_TO_RIGHT)</code> or
	 *            <code>isValid(B,A,RIGHT_TO_LEFT)</code>. <br>
	 *            If orientation is set to <code>null</code>, LEFT_TO_RIGHT is
	 *            assumed.<br>
	 *            If a provided entry is <code>null</code> the method assumes
	 *            that no entry for this module exists. <br>
	 *            You can also check entries for passed modules. In this case
	 *            {@link ModuleEntry#setPassed(boolean)} flag must be set to
	 *            <code>true</code>.
	 * 
	 * @return the result
	 */
	public abstract boolean isValid(ModuleEntry first, ModuleEntry second, ModuleOrientation orientation);

	/**
	 * 
	 * @return returns the textual description of the constraint
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *            the textual descriptions
	 */
	void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return returns the formal description of this constraints
	 */
	public String getFormalDescription() {
		return description;
	}

	/**
	 * 
	 * @param formalDescription
	 *            the formal description
	 */
	void setFormalDescription(String formalDescription) {
		this.formalDescription = formalDescription;
	}
};
