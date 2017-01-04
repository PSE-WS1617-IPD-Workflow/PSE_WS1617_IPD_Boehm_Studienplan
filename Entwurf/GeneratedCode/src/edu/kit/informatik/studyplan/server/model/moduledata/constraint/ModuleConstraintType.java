// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import edu.kit.informatik.studyplan.server.model.moduledata.ModuleOrientation;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;

/************************************************************/
/**
 * Modelliert eine Abhängkeitstyp einer Modulabhängigkeit
 */
public abstract class ModuleConstraintType {
	/**
	 *
	 */
	private int id;
	/**
	 * 
	 */
	private String description;
	/**
	 * 
	 */
	private String formalDescription;

	/**
	 * Überprüft, ob für zwei gegebene Moduleinträge die Abhängigkeit dieses Typs erfüllt ist.
	 * @param first der erste Moduleintrag
	 * @param second der zweite Moduleintrag
	 * @param orientation Richtung in die die Relation überprüft werden soll. <br>
	 *TODO: näher beschreiben
	 * @return Ergebnis der Überprüfung
	 */
	public abstract boolean isValid(ModuleEntry first, ModuleEntry second, ModuleOrientation orientation);

	/**
	 * 
	 * @return  gibt die textuelle Beschreibung der Abhängigkeit zurück
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * @param description die textuelle Beschreibung
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return  gibt die Abhängigkeitsbeschreibung in Form eines logischen Ausdrucks zurück
	 */
	public String getFormalDescription() {
		return description;
	}
	
	/**
	 * 
	 * @param formalDescription der logische Ausdruck
	 */
	public void setFormalDescription(String formalDescription) {
		this.formalDescription = formalDescription;
	}
};
