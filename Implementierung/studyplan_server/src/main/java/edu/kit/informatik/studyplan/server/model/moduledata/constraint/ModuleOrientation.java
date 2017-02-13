package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

/**
 * Directions for validating constraints.
 * 
 * @see ModuleConstraintType#isValid(edu.kit.informatik.studyplna.server.model.userdata.ModuleEntry,
 *      edu.kit.informatik.studyplna.server.model.userdata.ModuleEntry,
 *      ModuleOrientation)
 * 
 */
public enum ModuleOrientation {
	/**
	 * left to right orientation
	 */
	LEFT_TO_RIGHT,
	/**
	 * right to left orientation
	 */
	RIGHT_TO_LEFT;
};
