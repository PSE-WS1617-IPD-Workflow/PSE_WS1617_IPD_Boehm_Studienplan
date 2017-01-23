package edu.kit.informatik.studyplan.server.rest;

import java.util.List;

import edu.kit.informatik.studyplan.server.filter.FilterDescriptor;
import edu.kit.informatik.studyplan.server.rest.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Diese Klasse repräsentiert die Filter-Ressource.
 */
@Path("/filters")
public class FilterResource {


	/**
	 * GET Anfrage: Gibt eine Liste aller vorhandenen Filtern zurück.
	 * 
	 * @return eine Liste von Filtern.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONObject> getAllFilters() {
		return null;
	}
};
