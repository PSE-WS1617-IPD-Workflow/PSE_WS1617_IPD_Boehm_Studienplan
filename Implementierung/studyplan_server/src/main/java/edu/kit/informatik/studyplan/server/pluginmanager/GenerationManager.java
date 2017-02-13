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
 * Verwaltet den Zugriff auf das Generierungsplug-in. Das Generierungsplug-in
 * umfasst sowohl die Generierer-Schnittstelle als auch die
 * Zielfunktionen-Schnittstelle. Beide Schnittstellen werden mittels diese
 * Klasse adaptiert.
 */
public class GenerationManager {
	
	private static final double THRESHOLD = 0.5;

	/**
	 * Liste der Zielfunktionen.
	 * 
	 * @see edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction
	 */
	private List<PartialObjectiveFunction> objectiveFunctions;
	
	private ObjectiveFunction wrapper;

	/**
	 * Der Generierer.
	 * 
	 * @see edu.kit.informatik.studyplan.server.generation.Generator
	 */
	private Generator generator;

	/**
	 * Erstellt einen GenerationManager.
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
	 * Gibt den Generator zurück.
	 * 
	 * @return generator : der Generator
	 */
	public Generator getGenerator() {
		return generator;
	}

	/**
	 * Diese Methode ruft die generate Methode des
	 * {@link edu.kit.informatik.studyplan.server.generation.Generator }.
	 * 
	 * @param objectiveFunction
	 *            Die Zielfunktion, anhand der optimiert werden soll
	 * @param currentPlan
	 *            der bereits bestehende Plan
	 * @param moduleDAO
	 *            die Module
	 * @return ein vollständiger, korrekter und optimierter Studienplan vom Typ
	 *         Plan.
	 */
	public Plan generate(PartialObjectiveFunction objectiveFunction, Plan currentPlan, ModuleDao moduleDAO, Map<Field, Category>preferredSubjects, int maxECTSperSemester) {
		initWrapper();
		ThresholdObjectiveFunction thresholdObjectiveFunction = new ThresholdObjectiveFunction(THRESHOLD);
		thresholdObjectiveFunction.add(objectiveFunction);
		wrapper.add(thresholdObjectiveFunction);
		return generator.generate(wrapper, currentPlan, moduleDAO, preferredSubjects, maxECTSperSemester);
	}

	private void initWrapper() {
		wrapper = new MultiplicationObjectiveFunction();
		ObjectiveFunction average = new AverageObjectiveFunction();
		objectiveFunctions.stream().forEach(average::add);
		average.add(new MinimalStandardAverageDeviationECTSAtomObjectiveFunction());
		wrapper.add(average);
	}

	/**
	 * Gibt die Liste der Zielfunktionen zurück.
	 * 
	 * @return objectiveFunction : die Liste der Zielfunktionen
	 */
	public List<PartialObjectiveFunction> getAllObjectiveFunctions() {
		return objectiveFunctions;
	}

}
