// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.userdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;

/************************************************************/
/**
 * Modelliert einen Moduleintrag in einem Studienplan
 */
@Entity
@Table(name = "module_entry")
public class ModuleEntry {
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	
	public ModuleEntry() {
		
	}

	/**
	 * Creates a new ModuleEntry with a given module and semester.
	 * @param module the module to place inside the plan
	 * @param semester the number of the semester into which the module is placed
     */
	public ModuleEntry(Module module, int semester) {
		setModule(module);
		this.semester = semester;
	}

	public ModuleEntry() {
	}

	/**
	 * 
	 * @return gibt das Modul zurück
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
	 *            das Modul
	 */
	public void setModule(Module module) {
		this.moduleId = module.getIdentifier();
		this.module = module;
	}

	/**
	 * 
	 * @return gibt die Nummer des Semesters zurück, dem der Eintrag zugeordnet
	 *         wurde
	 */
	public int getSemester() {
		return semester;
	}

	/**
	 * 
	 * @param semester
	 *            die Semesternummer
	 */
	public void setSemester(int semester) {
		this.semester = semester;
	}
};
