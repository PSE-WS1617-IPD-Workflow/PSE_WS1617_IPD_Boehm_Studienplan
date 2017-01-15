/**
 * 
 */
package edu.kit.informatik.studyplan.server.model.moduledata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author NiklasUhl
 *
 */
@Entity
@Table(name = "field")
public class Field {
	@Id
	@Column(name = "field_id")
    private int fieldId;
	
	@Column(name = "name")
    private String name;
	
	@ManyToOne
	@JoinColumn(name = "discipline")
    private Discipline discipline;
	
	@Column(name = "min_ects")
    private double minEcts;
	
	@Column(name = "is_choosable")
    boolean isChoosable;
	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param discipline the discipline to set
	 */
	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}
	/**
	 * @param minEcts the minEcts to set
	 */
	public void setMinEcts(double minEcts) {
		this.minEcts = minEcts;
	}
	/**
	 * @param isChoosable the isChoosable to set
	 */
	public void setChoosable(boolean isChoosable) {
		this.isChoosable = isChoosable;
	}
}
