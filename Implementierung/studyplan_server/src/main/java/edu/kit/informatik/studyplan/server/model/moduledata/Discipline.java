package edu.kit.informatik.studyplan.server.model.moduledata;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import edu.kit.informatik.studyplan.server.rest.resources.StudentResource;

/************************************************************/
/**
 * Class modelling a discipline
 * @author NiklasUhl
 * @version 1.0
 */
@Entity
@Table(name = "discipline")
public class Discipline {
	
	@Id
	@Column(name = "discipline_id")
	@JsonProperty("id")
	@JsonView(StudentResource.Views.StudentClass.class)
	private int disciplineId = -1;
	
	@Column(name = "description")
	@JsonProperty("name") // Yes, name; see REST specs.
	@JsonView(StudentResource.Views.DisciplineClass.class)
	private String description;

	@OneToMany(mappedBy = "discipline")
	private List<Field> fields = new LinkedList<Field>();

	/**
	 * 
	 * @return returns the unique discipline id
	 */
	public int getDisciplineId() {
		return disciplineId;
	}

	/**
	 * @param disciplineId
	 *            the disciplineId to set
	 */
	void setDisciplineId(int disciplineId) {
		this.disciplineId = disciplineId;
	}

	/**
	 * 
	 * @return returns the discipline description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	 void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return returns the list of fields belonging to the discipline
	 */
	public List<Field> getFields() {
		return fields;
	}
}
