// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import edu.kit.informatik.studyplan.server.model.moduledata.ModuleOrientation;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;

/************************************************************/
/**
 * Modelliert eine zeitliche Abhängigkeit zwischen zwei Modulen:<br>
 * Die beiden Module können nicht im gleichen Semester belegt werden, da die
 * Veranstaltung zur gleichen Zeit stattfindet.
 */
public class OverlappingModuleConstraintType extends ModuleConstraintType {

	public boolean isValid(ModuleEntry first, ModuleEntry second, ModuleOrientation orientation) {
		return false;
	}
};