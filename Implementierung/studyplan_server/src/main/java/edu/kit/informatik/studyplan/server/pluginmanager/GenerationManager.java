package edu.kit.informatik.studyplan.server.pluginmanager;

import edu.kit.informatik.studyplan.server.generation.Generator;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.ObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.verification.Verifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Verwaltet den Zugriff auf das Generierungsplug-in. Das Generierungsplug-in
 * umfasst sowohl die Generierer-Schnittstelle als auch die
 * Zielfunktionen-Schnittstelle. Beide Schnittstellen werden mittels diese
 * Klasse adaptiert.
 */
public class GenerationManager {
	/**
	 * Erstellt einen GenerationManager.
	 */
	public GenerationManager() {

	}


	/**
	 * Gibt den Generator zurück.
	 * 
	 * @return generator : der Generator
	 */
	public Generator getGenerator() {
		Generator generator = null;
		ServiceLoader<Generator> loader = ServiceLoader.load(Generator.class);
		Iterator<Generator> iterator = loader.iterator();
		if (iterator.hasNext()) {
			generator = iterator.next();
		}
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
	public Plan generate(PartialObjectiveFunction objectiveFunction, Plan currentPlan, Map<Field, Category> preferredSubjects) {
		return getGenerator().generate(objectiveFunction, currentPlan, preferredSubjects);
	}


	/**
	 * Gibt die Liste der Zielfunktionen zurück.
	 * 
	 * @return objectiveFunction : die Liste der Zielfunktionen
	 */
	public List<PartialObjectiveFunction> getAllObjectiveFunction() {
		List<PartialObjectiveFunction> objectiveFunction = new ArrayList<PartialObjectiveFunction>();
		ServiceLoader<PartialObjectiveFunction> loader = ServiceLoader.load(PartialObjectiveFunction.class);
		Iterator<PartialObjectiveFunction> iterator = loader.iterator();
		if (iterator.hasNext()) {
			objectiveFunction.add(iterator.next());
		}
		return objectiveFunction;
	}

}
