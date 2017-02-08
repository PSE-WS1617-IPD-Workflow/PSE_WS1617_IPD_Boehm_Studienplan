/**
 * 
 */
package edu.kit.informatik.studyplan.server.model.moduledata;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;

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

	@OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Module> modules = new LinkedList<Module>();

	/**
	 * @return the unique field id
	 */
	public int getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId
	 *            the fieldId to set
	 */
	void setFieldId(int fieldId) {
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
	void setName(String name) {
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
	void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	/**
	 * @return the minimum value of credit points for the field
	 */
	public double getMinEcts() {
		return minEcts;
	}

	/**
	 * @param minEcts
	 *            the minEcts to set
	 */
	void setMinEcts(double minEcts) {
		this.minEcts = minEcts;
	}

	/**
	 * @return if the field allows a selection of subjects
	 */
	public boolean isChoosable() {
		return isChoosable;
	}

	/**
	 * set the field choosable
	 * @param isChoosable
	 *            if the field allows a selection of subjects
	 */
	void setChoosable(boolean isChoosable) {
		this.isChoosable = isChoosable;
	}

	/**
	 * @return list of modules belonging to the field
	 */
	public List<Module> getModules() {
		return modules;
	}

	/**
	 * @return returns the the field's subjects<br>
	 * returns <code>null</code> if field is not choosable
	 */
	@JsonProperty("categories")
	public List<Category> getSubjects() {
		if (isChoosable) {
			return ModuleDaoFactory.getModuleDao().getSubjects(this);
		} else {
			return null;
		}
	}
}
