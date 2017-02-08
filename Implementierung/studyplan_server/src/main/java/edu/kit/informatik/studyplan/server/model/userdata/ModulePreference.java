// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.userdata;

import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;

import javax.persistence.*;

/************************************************************/
/**
 * Modelliert eine Modulpräferenz. Eine Modulpräferenz bezeichnet die Bewertung
 * eines Moduls durch einen Nutzer.
 */
@Entity
@Table(name = "module_preference")
public class ModulePreference {

	@Id
	@Column(name = "preference_id")
	private int preferenceId;

	@Column(name = "module_id")
	private String moduleId;
	/**
	 * 
	 */
	@Transient
	private Module module;
	/**
	 * 
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "preference_type")
	private PreferenceType type;

	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Plan plan;

	/**
	 * Creates a new ModulePreference with given module and preference type
	 * @param module the module whose preference shall be set
	 * @param preference the preference to assign
     */
	public ModulePreference(Module module, PreferenceType preference) {
		this.module = module;
		this.type = preference;
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
	 * @return gibt den Typ der Präferenz zurück
	 * @see PreferenceType
	 */
	public PreferenceType getPreference() {
		return type;
	}

	/**
	 * 
	 * @param preferenceType
	 *            der Präferenztyp
	 */
	public void setPreference(PreferenceType preferenceType) {
	}
};
