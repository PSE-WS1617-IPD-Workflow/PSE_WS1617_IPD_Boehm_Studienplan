package edu.kit.informatik.studyplan.server.pluginmanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import edu.kit.informatik.studyplan.server.generation.Generator;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.AverageObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
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
	/**
	 * Liste der Zielfunktionen.
	 * 
	 * @see edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction
	 */
	private Collection<PartialObjectiveFunction> objectiveFunctions;

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
		objectiveFunctions.add(new AverageObjectiveFunction());
		//TODO: stuff
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
		return generator.generate(objectiveFunction, currentPlan, moduleDAO, preferredSubjects, maxECTSperSemester);
	}

	/**
	 * Gibt die Liste der Zielfunktionen zurück.
	 * 
	 * @return objectiveFunction : die Liste der Zielfunktionen
	 */
	public Collection<PartialObjectiveFunction> getAllObjectiveFunctions() {
		return objectiveFunctions;
	}

}
