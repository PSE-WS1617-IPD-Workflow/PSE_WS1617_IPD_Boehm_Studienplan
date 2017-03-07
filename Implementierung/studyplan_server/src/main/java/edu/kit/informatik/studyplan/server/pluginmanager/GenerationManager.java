package edu.kit.informatik.studyplan.server.pluginmanager;

import edu.kit.informatik.studyplan.server.generation.Generator;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.*;
import edu.kit.informatik.studyplan.server.generation.standard.SimpleGenerator;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	 * An objective function that sums all objective fuction given by the user in one that
	 * would be given to the generator. 
	 */
	private ObjectiveFunction wrapper;

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
		initWrapper();
		
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
			double minECTSperSemester) {
		initWrapper();
		ThresholdObjectiveFunction thresholdObjectiveFunction = new ThresholdObjectiveFunction(THRESHOLD);
		thresholdObjectiveFunction.add(objectiveFunction);
		wrapper.add(thresholdObjectiveFunction);
		return generator.generate(wrapper, currentPlan, moduleDAO, preferredSubjects, 
				maxECTSperSemester, minECTSperSemester);
	}
	/**
	 * Initializes the wrapper with adding the average of all objective functions to its 
	 * sub-functions list.  
	 */
	private void initWrapper() {
		wrapper = new MultiplicationObjectiveFunction();
		ObjectiveFunction average = new AverageObjectiveFunction();
		objectiveFunctions.forEach(average::add);
		average.add(new MinimalStandardAverageDeviationECTSAtomObjectiveFunction());
		wrapper.add(average);
	}

	/**
	 * @return the objectiveFunction
	 */
	public List<PartialObjectiveFunction> getAllObjectiveFunctions() {
		return objectiveFunctions;
	}

}
