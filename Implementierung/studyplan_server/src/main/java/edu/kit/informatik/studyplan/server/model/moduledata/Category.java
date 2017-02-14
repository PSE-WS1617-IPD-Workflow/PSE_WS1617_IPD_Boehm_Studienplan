package edu.kit.informatik.studyplan.server.model.moduledata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/************************************************************/
/**
 * Class modelling a module category.
 * 
 * @author NiklasUhl
 * @version 1.0
 */
@Entity
@Table(name = "category")
public class Category {

	@Id
	@Column(name = "category_id")
	@JsonProperty("id")
	private int categoryId = -1;

	@Column(name = "name")
	@JsonProperty("name")
	private String name;

	@Column(name = "is_subject")
	@JsonIgnore
	private boolean isSubject;

	/**
	 * 
	 * @return returns the unique category id
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * 
	 * @return returns the category name
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
	 * 
	 * @return returns if the category is a subject
	 */
	@JsonIgnore
	public boolean isSubject() {
		return isSubject;
	}

	/**
	 * @param isSubject
	 *            if category is subject
	 */
	void setSubject(boolean isSubject) {
		this.isSubject = isSubject;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Category)) {
			return false;
		} else {
			return ((Category) obj).getCategoryId() == this.getCategoryId();
		}
	}
};
