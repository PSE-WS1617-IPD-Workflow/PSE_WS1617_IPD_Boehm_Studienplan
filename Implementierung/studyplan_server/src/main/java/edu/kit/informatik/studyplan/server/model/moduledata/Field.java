/**
 * 
 */
package edu.kit.informatik.studyplan.server.model.moduledata;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class modelling a field for a discipline
 * @author NiklasUhl
 * @version 1.0
 */
@Entity
@Table(name = "field")
public class Field {
	@Id
	@Column(name = "field_id")
	@JsonProperty("id")
    private int fieldId = -1;
	
	@Column(name = "name")
	@JsonProperty("name")
    private String name;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "discipline_id")
	private Discipline discipline;

	@Column(name = "min_ects")
	@JsonProperty("min-ects")
    private double minEcts;
	
	@Column(name = "is_choosable")
	@JsonIgnore
	private boolean isChoosable;

	@OneToMany(mappedBy = "field")
	private List<Module> modules = new LinkedList<Module>();
	
	/**
	 * @return the fieldId
	 */
	public int getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId
	 *            the fieldId to set
	 */
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the discipline
	 */
	public Discipline getDiscipline() {
		return discipline;
	}

	/**
	 * @param discipline
	 *            the discipline to set
	 */
	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	/**
	 * @return the minEcts
	 */
	public double getMinEcts() {
		return minEcts;
	}

	/**
	 * @param minEcts
	 *            the minEcts to set
	 */
	public void setMinEcts(double minEcts) {
		this.minEcts = minEcts;
	}

	/**
	 * @return the isChoosable
	 */
	public boolean isChoosable() {
		return isChoosable;
	}

	/**
	 * @param isChoosable
	 *            the isChoosable to set
	 */
	public void setChoosable(boolean isChoosable) {
		this.isChoosable = isChoosable;
	}

	/**
	 * @return the modules
	 */
	public List<Module> getModules() {
		return modules;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Category> getSubjects() {
		//TODO: replace
		Set<Category> subjects = new HashSet<Category>();
		for (Module module : modules) {
			for (Category category : module.getCategories()) {
				if (category.isSubject()) {
					subjects.add(category);
				}
			}
		}
		return new LinkedList<>(subjects);
	}
}
