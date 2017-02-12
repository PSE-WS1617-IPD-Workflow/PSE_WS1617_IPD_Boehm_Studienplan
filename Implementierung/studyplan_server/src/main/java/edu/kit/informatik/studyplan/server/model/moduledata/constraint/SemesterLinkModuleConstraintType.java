package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;

/**
 * Models a semester link constraint.<br>
 * Both modules belong together and must be placed in the same semester.
 */
@Entity
@DiscriminatorValue(value = "semester_link")
public class SemesterLinkModuleConstraintType extends ModuleConstraintType {

	@Override
	public boolean isValid(ModuleEntry first, ModuleEntry second, ModuleOrientation orientation) {
		if (first == null || second == null) {
			return false;
		}
		if (first.isPassed() && second.isPassed()) {
			return true;
		}
		return first.getSemester() == second.getSemester();
	}
};
