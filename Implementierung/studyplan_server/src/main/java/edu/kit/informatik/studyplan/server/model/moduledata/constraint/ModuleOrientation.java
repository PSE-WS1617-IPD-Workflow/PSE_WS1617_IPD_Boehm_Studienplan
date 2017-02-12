package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;

/**
 * Directions for validating constraints.
 * @see ModuleConstraintType#isValid(ModuleEntry, ModuleEntry, ModuleOrientation)
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
