package edu.kit.informatik.studyplan.server.rest.resources;

import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.pluginmanager.GenerationManager;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Map;

/**
 * REST resource for /objective-functions.
 */
@Path("/objective-functions")
public class ObjectiveFunctionResource {
	/**
	 * GET request handler.
	 * 
	 * @return a list of all available objective functions as JSON.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Collection<PartialObjectiveFunction>> getAllObjectiveFunctions() {
		//TODO @annotate (Partial?)ObjectiveFunction; add its missing attributes. (Wait for Nada)
		return SimpleJsonResponse.build("functions", new GenerationManager().getAllObjectiveFunctions());
	}
}
