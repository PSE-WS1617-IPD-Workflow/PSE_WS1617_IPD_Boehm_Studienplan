package edu.kit.informatik.studyplan.server.model.moduledata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import edu.kit.informatik.studyplan.server.rest.resources.StudentResource;

/************************************************************/
/**
 * Class modeling a discipline
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
	@JsonIgnore
	private List<Field> fields = new LinkedList<Field>();

	@OneToMany(mappedBy = "discipline")
    @JsonIgnore
	private List<RuleGroup> ruleGroups = new LinkedList<RuleGroup>();
	
	@OneToMany(mappedBy = "discipline")
	@Where(clause = "is_compulsory = true")
	private List<Module> compulsoryModules = new LinkedList<Module>();

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
	public void setDisciplineId(int disciplineId) {
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
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return returns the list of fields belonging to the discipline
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 *
	 * @return returns a list of all rule groups associated with this discipline
	 */
	public List<RuleGroup> getRuleGroups() {
		return ruleGroups;
	}

	/**
	 * @return the compulsoryModules
	 */
	public List<Module> getCompulsoryModules() {
		return compulsoryModules;
	}
}
