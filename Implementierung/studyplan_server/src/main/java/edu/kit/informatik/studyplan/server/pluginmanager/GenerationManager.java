package edu.kit.informatik.studyplan.server.pluginmanager;

import java.util.Collection;

import edu.kit.informatik.studyplan.server.generation.Generator;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * Verwaltet den Zugriff auf das Generierungsplug-in.  
 * Das Generierungsplug-in umfasst sowohl die Generierer-Schnittstelle als auch 
 * die Zielfunktionen-Schnittstelle. Beide Schnittstellen werden mittels diese Klasse adaptiert.
 */
public class GenerationManager {
	/**
	 * Erstellt einen GenerationManager.
	 */
	public GenerationManager(){
		
	}
	/**
	 * Der Generierer.
	 * @see edu.kit.informatik.studyplan.server.generation.Generator
	 */
	private Generator generator;
	/**
	 * Gibt den Generator zurück.
	 * @return generator : der Generator
	 */
	public Generator getGenerator() {
		return generator;
	}
	/**
	 * Diese Methode ruft die generate Methode des
	 * {@link edu.kit.informatik.studyplan.server.generation.Generator }.
	 * @param objectiveFunction Die Zielfunktion, anhand der optimiert werden soll
	 * @param currentPlan der bereits bestehende Plan
	 * @param moduleDAO die Module
	 * @return ein vollständiger, korrekter und optimierter Studienplan vom Typ Plan.
	 */
	public Plan generate(PartialObjectiveFunction objectiveFunction, Plan currentPlan, ModuleDao moduleDAO){
		return null;
	}
	/**
	 * Liste der Zielfunktionen.
	 * @see edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction
	 */
	private Collection<PartialObjectiveFunction> objectiveFunction;
	/**
	 * Gibt die Liste der Zielfunktionen zurück.
	 * @return objectiveFunction : die Liste der Zielfunktionen
	 */
	public Collection<PartialObjectiveFunction> getObjectiveFunction() {
		return objectiveFunction;
	}
	  /**
	   * Diese Methode ruft die evaluate Methode der 
	   * {@link edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction}.
	   * @return Wert zwischen 0 und 1 der den Plan evaluiert.
	   * @param plan der zu bewertende Plan.
	   */
	  public double evaluate(Plan plan){
		return 0;
	  }

}
