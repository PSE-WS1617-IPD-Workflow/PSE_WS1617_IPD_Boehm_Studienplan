package edu.kit.informatik.studyplan.server.generation;

import java.util.Map;

import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/************************************************************/
/**
 * The Generator interface provides the general structure of a generator.
 */
public interface Generator {

	/**
	 * The generate method generates a complete, optimized and correct study
	 * plan. For this purpose, it accepts an initial study plan, completes it
	 * first by the modules' constraints and random choosing of appropriate
	 * modules, and then optimizes it with the aid of the given objective
	 * function. The required modules are reached via moduleDao.
	 * 
	 * 
	 * @return a complete, optimized and correct study plan from type plan.
	 * @param objectiveFunction
	 *            the objective function, according to which the plan would be
	 *            optimized.
	 * @param currentPlan
	 *            the already existing plan
	 */
	public Plan generate(PartialObjectiveFunction objectiveFunction, Plan currentPlan, ModuleDao moduleDAO,
			Map<Field, Category> preferredSubjects, int maxECTSperSemester);
}
