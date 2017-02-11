package edu.kit.informatik.studyplan.server.model.moduledata;

import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "description_id")
	private ModuleDescription moduleDescription;
	
	@ManyToOne
	@JoinColumn(name = "discipline_id")
	private Discipline discipline;
	
	@ManyToOne
	@JoinColumn(name = "field_id")
	private Field field;

	@OneToMany(mappedBy = "secondModule", fetch = FetchType.LAZY)
	private List<ModuleConstraint> toConstraints = new LinkedList<ModuleConstraint>();

	@OneToMany(mappedBy = "firstModule", fetch = FetchType.LAZY)
	private List<ModuleConstraint> fromConstraints = new LinkedList<ModuleConstraint>();

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
	 * @return the toConstraints
	 */
	List<ModuleConstraint> getToConstraints() {
		return toConstraints;
	}

	/**
	 * @param toConstraints the toConstraints to set
	 */
	void setToConstraints(List<ModuleConstraint> toConstraints) {
		this.toConstraints = toConstraints;
	}

	/**
	 * @return the fromConstraints
	 */
	List<ModuleConstraint> getFromConstraints() {
		return fromConstraints;
	}

	/**
	 * @param fromConstraints the fromConstraints to set
	 */
	void setFromConstraints(List<ModuleConstraint> fromConstraints) {
		this.fromConstraints = fromConstraints;
	}

	/**
	 * @param identifier the identifier to set
	 */
	void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @param name the name to set
	 */
	void setName(String name) {
		this.name = name;
	}

	/**
	 * @param creditPoints the creditPoints to set
	 */
	void setCreditPoints(double creditPoints) {
		this.creditPoints = creditPoints;
	}

	/**
	 * @param cycleType the cycleType to set
	 */
	void setCycleType(CycleType cycleType) {
		this.cycleType = cycleType;
	}

	/**
	 * @param compulsory the compulsory to set
	 */
	void setCompulsory(boolean compulsory) {
		this.compulsory = compulsory;
	}

	/**
	 * @param moduleDescription the moduleDescription to set
	 */
	void setModuleDescription(ModuleDescription moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	/**
	 * @param discipline the discipline to set
	 */
	void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	/**
	 * @param field the field to set
	 */
	void setField(Field field) {
		this.field = field;
	}

	/**
	 * @param categories the categories to set
	 */
	void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public boolean equals(Object obj) {
		return getClass() == obj.getClass()
				&& getIdentifier().equals(((Module) obj).getIdentifier());
	}
}
