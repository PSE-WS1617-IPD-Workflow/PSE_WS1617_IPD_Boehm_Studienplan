package edu.kit.informatik.studyplan.server.model.moduledata;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;

/**
 * Class modelling a module
 * 
 * @author NiklasUhl
 * @version 1.0
 */
@Entity
@Table(name = "module")
public class Module {
	
	@Column(name = "identifier")
	@Id
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "description_id")
	private ModuleDescription moduleDescription;
	
	@JoinColumn(name = "discipline_id")
	private Discipline discipline;
	
	@ManyToOne
	@JoinColumn(name = "field_id")
	private Field field;

	@OneToMany(mappedBy = "secondModule", fetch = FetchType.LAZY)
	private List<ModuleConstraint> toConstraints = new LinkedList<ModuleConstraint>();

	@OneToMany(mappedBy = "firstModule", fetch = FetchType.LAZY)
	private List<ModuleConstraint> fromConstraints = new LinkedList<ModuleConstraint>();

	@Transient
	private List<ModuleConstraint> moduleConstraints = new LinkedList<ModuleConstraint>();

	@ManyToMany(fetch = FetchType.LAZY)
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
}
