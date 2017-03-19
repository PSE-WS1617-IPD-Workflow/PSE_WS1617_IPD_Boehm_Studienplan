package edu.kit.informatik.studyplan.server.generation;

import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

import java.util.Map;

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
	 * @param objectiveFunction
	 *            the objective function according to which the plan would be
	 *            evaluated and optimized.
	 * @param currentPlan
	 *            the already existing plan.
	 * @param moduleDAO
	 *            the ModuleDao to fetch the modules from the database.
	 * @param preferredSubjects
	 *            the preferred subjects per field (for the Generator to choose
	 *            modules from)
	 * @param maxECTSperSemester
	 *            the maximum number of credit points per semester
	 * @param minECTSperSemester
	 *            the minimum number of credit points per semester
	 * @param minSemesterNum
	 * 			the minimum number of semesters that should be in the plan
	 * @param maxSemesterNum
	 * 			the minimum number of semesters that should be in the plan
	 * @return a complete, correct and optimized study plan with type `Plan` if
	 *         such a plan could be created, if not this method returns an invalid plan.
	 */
	Plan generate(PartialObjectiveFunction objectiveFunction, Plan currentPlan, ModuleDao moduleDAO,
			Map<Field, Category> preferredSubjects, double maxECTSperSemester, double minECTSperSemester,
			int minSemesterNum, int maxSemesterNum);
}
