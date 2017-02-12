package edu.kit.informatik.studyplan.server.rest.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.dao.AuthorizationContext;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

//import com.sun.xml.internal.ws.client.RequestContext;

/**
 * REST resource for /fields.
 */
@Path("/fields")
@AuthorizationNeeded
public class FieldsResource {
	@Inject
	Provider<AuthorizationContext> context;

	private User getUser() {
		return context.get().getUser();
	}

	/**
	 * GET request handler.
	 * 
	 * @return all chooseable fields (for generation wizard)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChooseableFields() {
		if (getUser().getDiscipline() == null) {
			throw new UnprocessableEntityException();
		}
		Map<String, List<FieldDto>> result = Utils.withModuleDao(moduleDao -> {
			List<FieldDto> choosableFields = moduleDao.getFields(getUser().getDiscipline()).stream()
					.filter(Field::isChoosable)
					.map(field -> new FieldDto(field))
					.collect(Collectors.toList());
			return SimpleJsonResponse.build("fields", choosableFields);
		});
		return Response.ok(result).build();
	}

	public class FieldDto {
		
		@JsonProperty
		int id;

		@JsonProperty
		String name;

		@JsonProperty("min-ects")
		double minEcts;

		@JsonProperty
		List<SubjectDto> categories;

		public FieldDto(Field field) {
			this.id = field.getFieldId();
			this.name = field.getName();
			this.minEcts = field.getMinEcts();
			categories = new ArrayList<SubjectDto>();
			for (Category subject : field.getSubjects()) {
				categories.add(new SubjectDto(subject));
			}
		}

		public class SubjectDto {

			@JsonProperty
			int id;

			@JsonProperty
			String name;

			public SubjectDto(Category category) {
				this.id = category.getCategoryId();
				this.name = category.getName();
			}
		}
	}

}
