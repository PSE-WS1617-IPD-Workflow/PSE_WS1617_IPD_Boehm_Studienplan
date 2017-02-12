package edu.kit.informatik.studyplan.server.model.userdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;

/**
 * Class modeling a module entry, which assigns a module to a semester
 */
@Entity
@Table(name = "module_entry")
public class ModuleEntry {
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "entry_id")
	private int id;

	@Column(name = "module_id")
	private String moduleId;

	@Transient
	private Module module;
	/**
	 * 
	 */
	@Column(name = "semester")
	private int semester;

	@Transient
	private boolean isPassed;

	/**
	 * creates a new empty module entry
	 */
	public ModuleEntry() {
	}

	/**
	 * Creates a new ModuleEntry with a given module and semester.
	 * 
	 * @param module
	 *            the module to place inside the plan
	 * @param semester
	 *            the number of the semester into which the module is placed
	 */
	public ModuleEntry(Module module, int semester) {
		setModule(module);
		this.semester = semester;
	}

	/**
	 * 
	 * @return the module
	 */
	public Module getModule() {
		if (module == null) {
			module = ModuleDaoFactory.getModuleDao().getModuleById(moduleId);
		}
		return module;
	}

	/**
	 * 
	 * @param module
	 *            the module
	 */
	public void setModule(Module module) {
		this.moduleId = module.getIdentifier();
		this.module = module;
	}

	/**
	 * 
	 * @return returns the semester number, the entry was assigned to
	 */
	public int getSemester() {
		return semester;
	}

	/**
	 * 
	 * @param semester
	 *            the semester number
	 */
	public void setSemester(int semester) {
		this.semester = semester;
	}

	/**
	 * A caller may manually mark an entry as passed to provide this information
	 * to a Verifier.
	 * 
	 * @return returns if the isPassed flag is set to true.
	 * @see ModuleConstraintType#isValid(ModuleEntry, ModuleEntry,
	 *      edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleOrientation)
	 */
	public boolean isPassed() {
		return isPassed;
	}

	/**
	 * A caller may manually mark an entry as passed to provide this information
	 * to a Verifier.
	 * 
	 * @param isPassed
	 *            the isPassed flag to set
	 * @see ModuleConstraintType#isValid(ModuleEntry, ModuleEntry,
	 *      edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleOrientation)
	 */
	public void setPassed(boolean isPassed) {
		this.isPassed = isPassed;
	}
};
