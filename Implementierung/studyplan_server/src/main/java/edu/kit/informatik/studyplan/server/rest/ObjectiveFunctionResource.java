package edu.kit.informatik.studyplan.server.rest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.kit.informatik.studyplan.server.generation.objectivefunction.ObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.pluginmanager.GenerationManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Diese Klasse repräsentiert die Zielfunktion-Ressource.
 */
@Path("/objective-functions")
public class ObjectiveFunctionResource {

	/**
	 * Einen
	 * {@link edu.kit.informatik.studyplan.server.pluginmanager.GenerationManager}
	 * Instanz um auf den Generierer zugreifen zu können.
	 */
	private GenerationManager generationManager; //TODO

	/**
	 * Gibt den generierungsmanager zurück.
	 * 
	 * @return der generationManager
	 */
	public GenerationManager getGenerationManager() {
		return generationManager;
	} //TODO Why???

	/**
	 * GET-Anfrage: Gibt eine Liste mit allen vorhandenen Zielfunktionen als
	 * JSON Objekte zurück.
	 * 
	 * @return Liste mit allen vorhandenen Zielfunktionen als JSON Objekte.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Collection<PartialObjectiveFunction>> getAllObjectiveFunctions() {
		//TODO @annotate (Partial?)ObjectiveFunction; add its missing attributes.
		return SimpleJsonResponse.build("functions", new GenerationManager().getObjectiveFunction());
	}
}
