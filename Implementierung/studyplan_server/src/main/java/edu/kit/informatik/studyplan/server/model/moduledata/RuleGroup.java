package edu.kit.informatik.studyplan.server.model.moduledata;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a rule group, which defines a group of modules
 * where a number of modules in a specified range is necessary for completing a discipline
 * @author NiklasUhl
 *
 */
@Entity
@Table(name = "rule_group")
public class RuleGroup {
	
	@Id
	@Column(name = "rule_id")
	@JsonIgnore
	private int ruleId;
	
	@Column(name = "name")
	@JsonProperty("name")
	private String name;
	
	@Column(name = "min_num")
	@JsonProperty("min-ects")
	private int minNum = -1;
	
	@Column(name = "max_num")
	@JsonProperty("max-ects")
	private int maxNum = -1;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rule_module_assignment",
	joinColumns = 
		@JoinColumn(name = "module_id"),
	inverseJoinColumns =
		@JoinColumn(name = "rule_id"))
	@JsonIgnore
	private List<Module> modules = new LinkedList<Module>();
	
	@JoinColumn(name = "discipline_id")
	@ManyToOne
	private Discipline discipline;

	/**
	 * @return the unique rule id
	 */
	public int getRuleId() {
		return ruleId;
	}

	/**
	 * @return the rule name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the minimum number of modules for this group
	 */
	public int getMinNum() {
		return minNum;
	}

	/**
	 * @return the maximum number of modules for this group
	 */
	public int getMaxNum() {
		return maxNum;
	}

	/**
	 * @return returns a list of modules belonging to this group
	 */
	public List<Module> getModules() {
		return modules;
	}

	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param minNum the minNum to set
	 */
	public void setMinNum(int minNum) {
		this.minNum = minNum;
	}

	/**
	 * @param maxNum the maxNum to set
	 */
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	
}
