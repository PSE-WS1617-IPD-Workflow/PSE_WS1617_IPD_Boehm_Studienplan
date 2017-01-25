package edu.kit.informatik.studyplan.server.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.kit.informatik.studyplan.server.filter.FilterDescriptor;
import edu.kit.informatik.studyplan.server.filter.FilterDescriptorProvider;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.rest.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Diese Klasse repräsentiert die Filter-Ressource.
 */
@Path("/filters")
public class FilterResource {
	@Inject
	AuthorizationContext context;

	/**
	 * GET Anfrage: Gibt eine Liste aller vorhandenen Filtern zurück.
	 * 
	 * @return eine Liste von Filtern.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String,Object>> getAllFilters() {
		context = new AuthorizationContext();
		context.setUser(new User());
		context.getUser().setDiscipline(new Discipline());
//		if (context.getUser().getDiscipline() == null)
//			throw new UnprocessableEntityException();
		return new FilterDescriptorProvider(context.getUser().getDiscipline()).values().parallelStream()
				.map(FilterDescriptor::toJson)
				.collect(Collectors.toList());
	}
};
