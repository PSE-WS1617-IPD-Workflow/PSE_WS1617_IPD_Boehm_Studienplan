package edu.kit.informatik.studyplan.server.rest;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//import com.sun.xml.internal.ws.client.RequestContext;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;

@Path("/fields")
public class FieldsResource {
    @Inject
    AuthorizationContext context;

    /**
     *
     * @return all choosable fields (for generation wizard)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,List<Field>> getChoosableFields() {
        if (context.getUser().getDiscipline() == null)
            throw new UnprocessableEntityException();
        List<Field> choosableFields = ModuleDaoFactory.getModuleDao().getFields(context.getUser().getDiscipline())
                .parallelStream()
                .filter(Field::isChoosable)
                .collect(Collectors.toList());
        return SimpleJsonResponse.build("fields", choosableFields);
    }

    /**
     *
     * @param id the ID of the field
     * @return all categories from a field's modules with isSubject
     */
    @GET
    @Path("/{id}/options")
    public Map<String,Set<Category>> getOptions(@PathParam("id") Integer id) {
        Field field = ModuleDaoFactory.getModuleDao().getFieldById(id);
        if (field == null)
            throw new BadRequestException();
        Set<Category> result = field.getModules().parallelStream()
                .flatMap(module -> module.getCategories().stream()
                        .filter(Category::isSubject))
                .collect(Collectors.toSet());
        return SimpleJsonResponse.build("options", result);
    }
}
