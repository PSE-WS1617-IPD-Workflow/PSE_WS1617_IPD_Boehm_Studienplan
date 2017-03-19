package edu.kit.informatik.studyplan.server.model.userdata;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;

/**
 * Class modeling a user.
 */
@Entity
@Table(name = "user")
public class User implements Principal {
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@NaturalId
	@Column(name = "name")
	private String userName;
	
	@Column(name = "discipline_id")
	private int disciplineId;

	@Transient
	private Discipline discipline;
	
	@Embedded
	private Semester studyStart;
	
	@OneToMany(orphanRemoval =  true)
	@Cascade(CascadeType.ALL)
	@JoinTable(name = "passed_modules", 
		joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "entry_id"))
	private List<ModuleEntry> passedModules = new LinkedList<ModuleEntry>();

	@OneToMany(mappedBy = "user")
	@Cascade(CascadeType.ALL)
	private List<Plan> plans = new LinkedList<Plan>();
	
	@OneToMany(mappedBy = "user")
	@Cascade(CascadeType.DELETE)
	private List<AuthorizationContext> contexts = new LinkedList<AuthorizationContext>();

	/**
	 * 
	 * @return returns the unique user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param userName
	 *            the username
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @return returns the discipline
	 */
	public Discipline getDiscipline() {
		if (discipline == null) {
			discipline = ModuleDaoFactory.getModuleDao().getDisciplineById(disciplineId);
		}
		return discipline;
	}

	/**
	 * 
	 * @param discipline
	 *            the discipline to assign
	 */
	public void setDiscipline(Discipline discipline) {
		this.disciplineId = discipline.getDisciplineId();
		this.discipline = discipline;
	}

	/**
	 * 
	 * @return returns the semester of study start
	 */
	public Semester getStudyStart() {
		return studyStart;
	}

	/**
	 * 
	 * @param semester
	 *            the semester of study start to set
	 */
	public void setStudyStart(Semester semester) {
		this.studyStart = semester;
	}

	/**
	 * 
	 * @return returns a list of module entries for all passed modules
	 */
	public List<ModuleEntry> getPassedModules() {
		return passedModules;
	}

	/**
	 * 
	 * @return returns a list of all plans belonging to that user
	 */
	public List<Plan> getPlans() {
		return plans;
	}

	@Override
	@Transient
	public String getName() {
		return getUserName();
	}
};
