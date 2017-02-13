package edu.kit.informatik.studyplan.server.model.moduledata;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;

/**
 * Class modeling a module
 * 
 * @author NiklasUhl
 * @version 1.0
 */
@Entity
@Table(name = "module")
public class Module {
	
	@Column(name = "module_id")
	@Id
	private int moduleId;
	
	@Column(name = "identifier")
	@NaturalId
	private String identifier;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "credit_points")
	private double creditPoints;
	
	@Column(name = "cycle_type")
	@Enumerated(EnumType.STRING)
	private CycleType cycleType;
	
	@Column(name = "is_compulsory")
	private boolean compulsory;

	@JoinColumn(name = "description_id")
	@ManyToOne
	private ModuleDescription moduleDescription;
	
	@ManyToOne
	@JoinColumn(name = "discipline_id")
	private Discipline discipline;
	
	@ManyToOne
	@JoinColumn(name = "field_id")
	private Field field;

	@OneToMany(mappedBy = "secondModule")
	private List<ModuleConstraint> toConstraints = new LinkedList<ModuleConstraint>();

	@OneToMany(mappedBy = "firstModule")
	private List<ModuleConstraint> fromConstraints = new LinkedList<ModuleConstraint>();

	@ManyToMany
	@JoinTable(name = "module_category_assignment",
		joinColumns = @JoinColumn(name = "module_id"), 
		inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories;

	/**
	 * 
	 * @return returns the module's unique identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * 
	 * @return returns the module's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return returns the number of of credit points
	 */
	public double getCreditPoints() {
		return creditPoints;
	}

	/**
	 * 
	 * @return returns the {@plainlink CycleType} of the module
	 */
	public CycleType getCycleType() {
		return cycleType;
	}

	/**
	 * 
	 * @return returns whether the module is compulsory
	 */
	public boolean isCompulsory() {
		return compulsory;
	}

	/**
	 * 
	 * @return returns the description of the module,
	 * <code>null</code> if no description is provided
	 */
	public ModuleDescription getModuleDescription() {
		return moduleDescription;
	}

	/**
	 * 
	 * @return returns the module's discipline
	 */
	public Discipline getDiscipline() {
		return discipline;
	}

	/**
	 * @return returns the field the module is assigned to
	 */
	public Field getField() {
		return field;
	}

	/**
	 * 
	 * @return returns a list of all constraints to other modules
	 */
	public List<ModuleConstraint> getConstraints() {
		List<ModuleConstraint> moduleConstraints = new LinkedList<ModuleConstraint>();
		moduleConstraints.addAll(fromConstraints);
		moduleConstraints.addAll(toConstraints);
		return moduleConstraints;
	}

	/**
	 * 
	 * @return returns a list of all categories the module is contained in
	 */
	public List<Category> getCategories() {
		return categories;
	}

	/**
	 * @return the constraint with this module as second module
	 */
	public List<ModuleConstraint> getToConstraints() {
		return toConstraints;
	}

	/**
	 * @return the constraint with this module as first module
	 */
	public List<ModuleConstraint> getFromConstraints() {
		return fromConstraints;
	}

	/**
	 * @param identifier the unique identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @param name the module name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param creditPoints the creditPoints to set
	 */
	public void setCreditPoints(double creditPoints) {
		this.creditPoints = creditPoints;
	}

	/**
	 * @param cycleType the cycleType to set
	 */
	public void setCycleType(CycleType cycleType) {
		this.cycleType = cycleType;
	}

	/**
	 * @param compulsory module is compulsory
	 */
	public void setCompulsory(boolean compulsory) {
		this.compulsory = compulsory;
	}

	/**
	 * @param moduleDescription the moduleDescription to set
	 */
	public void setModuleDescription(ModuleDescription moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	/**
	 * @param discipline the discipline to set
	 */
	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(Field field) {
		this.field = field;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Module)) {
			return false;
		} else {
			return ((Module) obj).getIdentifier().equals(this.getIdentifier());
		}
	}
}
