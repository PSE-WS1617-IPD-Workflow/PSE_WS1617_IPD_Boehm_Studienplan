package edu.kit.informatik.studyplan.server.model.moduledata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

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
	@JsonIgnore
	private Discipline discipline;

	/**
	 * @return the ruleId
	 */
	public int getRuleId() {
		return ruleId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the minNum
	 */
	public int getMinNum() {
		return minNum;
	}

	/**
	 * @return the maxNum
	 */
	public int getMaxNum() {
		return maxNum;
	}

	/**
	 * @return the modules
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
