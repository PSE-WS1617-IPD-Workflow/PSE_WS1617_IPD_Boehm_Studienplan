package edu.kit.informatik.studyplan.server.pluginmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.kit.informatik.studyplan.server.generation.Generator;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.AverageObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.MinimalECTSAtomObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.MinimalSemestersAtomObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.MinimalStandardAverageDeviationECTSAtomObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.ModulePreferencesAtomObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.MultiplicationObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.ObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.ThresholdObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.standard.SimpleGenerator;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * Manages the access to the generation plug-in. The generation plug-in includes both 
 * the generator interface and the target function interface.
 */
public class GenerationManager {
	
	private static final double THRESHOLD = 0.5;

	/**
	 * List of objective functions.
	 * 
	 * @see edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction
	 */
	private List<PartialObjectiveFunction> objectiveFunctions;
	/**
	 * The generator.
	 * 
	 * @see edu.kit.informatik.studyplan.server.generation.Generator
	 */
	private Generator generator;

	/**
	 * Creates a generation Manager.
	 */
	public GenerationManager() {
		generator = new SimpleGenerator();
		objectiveFunctions = new ArrayList<PartialObjectiveFunction>();
		objectiveFunctions.add(new MinimalECTSAtomObjectiveFunction());
		objectiveFunctions.add(new MinimalSemestersAtomObjectiveFunction());
		objectiveFunctions.add(new ModulePreferencesAtomObjectiveFunction());
	}

	/**
	 * Returns the generator
	 * 
	 * @return the generator 
	 */
	public Generator getGenerator() {
		return generator;
	}

	/**
	 * This method calls the generate method of the Generator Interface after 
	 * initialising tha wrapper objective function to pass it as a parameter.
	 * @param objectiveFunction
	 *            the objective function according to which the plan would be 
	 *            evaluated and optimized.
	 * @param currentPlan
	 *            the already existing plan.
	 * @param moduleDAO
	 *            the ModuleDao to fetch the modules from the database.
	 * @param preferredSubjects
	 * 			  the preferred subjects per field (for the Generator to choose modules from)
	 * @param maxECTSperSemester
	 * 			  the maximum number of credit points per semester
	 * @param minECTSperSemester
	 * 			  the minimum number of credit points per semester
	 * @return a complete, correct and optimized study plan with type `Plan`
	 */
	public Plan generate(PartialObjectiveFunction objectiveFunction, 
			Plan currentPlan, 
			ModuleDao moduleDAO, 
			Map<Field, Category>preferredSubjects, 
			double maxECTSperSemester,
			double minECTSperSemester,
			int minSemesterNum,
			int maxSemesterNum) {
		ObjectiveFunction wrapper = wrap(objectiveFunction);
		return generator.generate(wrapper, currentPlan, moduleDAO, preferredSubjects, 
				maxECTSperSemester, minECTSperSemester, minSemesterNum, maxSemesterNum);
	}
	
	/**
	 * wraps an objective function to ensure high quality output
	 * @param objectiveFunction the partial objective function
	 * @return the wrapper
	 */
	public ObjectiveFunction wrap(PartialObjectiveFunction objectiveFunction) {
		ObjectiveFunction wrapper = initWrapper();
		ThresholdObjectiveFunction thresholdObjectiveFunction = new ThresholdObjectiveFunction(THRESHOLD);
		thresholdObjectiveFunction.add(objectiveFunction);
		wrapper.add(thresholdObjectiveFunction);
		return wrapper;
	}

	/**
	 * Initializes the wrapper with adding the average of all objective functions to its 
	 * sub-functions list.  
	 */
	private ObjectiveFunction initWrapper() {
		ObjectiveFunction wrapper = new MultiplicationObjectiveFunction();
		ObjectiveFunction average = new AverageObjectiveFunction();
		objectiveFunctions.forEach(average::add);
		average.add(new MinimalStandardAverageDeviationECTSAtomObjectiveFunction());
		wrapper.add(average);
		return wrapper;
	}

	/**
	 * @return the objectiveFunction
	 */
	public List<PartialObjectiveFunction> getAllObjectiveFunctions() {
		return objectiveFunctions;
	}

}
