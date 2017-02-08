package edu.kit.informatik.studyplan.server.rest.resources;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.kit.informatik.studyplan.server.filter.FilterDescriptor;
import edu.kit.informatik.studyplan.server.filter.FilterDescriptorProvider;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;

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
		Discipline discipline = context.getUser().getDiscipline();
		if (discipline == null)
			throw new UnprocessableEntityException();
		return new FilterDescriptorProvider(discipline).values().parallelStream()
				.map(FilterDescriptor::toJson)
				.collect(Collectors.toList());
	}
};
