package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;

/**
 * Modeling a relation between two modules, that they may not be placed in the
 * same semester because they overlap.
 */
@Entity
@DiscriminatorValue(value = "overlapping")
public class OverlappingModuleConstraintType extends ModuleConstraintType {

	@Override
	public boolean isValid(ModuleEntry first, ModuleEntry second, ModuleOrientation orientation) {
		if (first != null && second != null && !first.isPassed() && !second.isPassed()
				&& first.getSemester() == second.getSemester()) {
			return false;
		}
		return true;
	}
};
