package edu.kit.informatik.studyplan.server.rest.resources;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.pluginmanager.GenerationManager;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

/**
 * REST resource for /objective-functions.
 */
@Path("/objective-functions")
@AuthorizationNeeded
public class ObjectiveFunctionResource {
	/**
	 * GET request handler.
	 * 
	 * @return a list of all available objective functions as JSON.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List<FunctionDto>> getAllObjectiveFunctions() {
		List<PartialObjectiveFunction> objectiveFunctions = new GenerationManager().getAllObjectiveFunctions();
		List<FunctionDto> result = objectiveFunctions.stream()
			.map(function -> new FunctionDto(objectiveFunctions.indexOf(function), function))
			.collect(Collectors.toList());
		return SimpleJsonResponse.build("functions", result);
	}
	
	/**
	 * DataTransferObject for objective functions
	 * @author NiklasUhl
	 *
	 */
	public static class FunctionDto {
		
		@JsonProperty
		int id;
		
		@JsonProperty
		String name;
		
		/**
		 * Constructs an instance with the given id from the given function
		 * @param id the id
		 * @param function the function
		 */
		public FunctionDto(int id, PartialObjectiveFunction function) {
			this.id = id;
			this.name = function.getDescriptor();
		}
		
	}
}
