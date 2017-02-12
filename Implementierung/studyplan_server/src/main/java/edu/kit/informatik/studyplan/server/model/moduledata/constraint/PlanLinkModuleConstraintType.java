package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;

/**
 * Models a constraint where both modules must be contained in the plan.
 */
@Entity
@DiscriminatorValue(value = "plan_link")
public class PlanLinkModuleConstraintType extends ModuleConstraintType {

	@Override
	public boolean isValid(ModuleEntry first, ModuleEntry second, ModuleOrientation orientation) {
		if (first != null && first.isPassed() && second == null) {
			return false;
		}
		if (second != null && second.isPassed() && first == null) {
			return false;
		}
		return first != null && second != null;
	}
};
