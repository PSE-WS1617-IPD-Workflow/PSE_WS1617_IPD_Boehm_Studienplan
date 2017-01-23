package edu.kit.informatik.studyplan.server.rest;

import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/fields")
public class FieldsResource {
    @Context
    AuthorizationContext context;

    /**
     *
     * @return all choosable fields (for generation wizard)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,List<Field>> getChoosableFields() {
        List<Field> choosableFields = ModuleDaoFactory.getModuleDao().getFields(context.getUser().getDiscipline())
                .parallelStream()
                .filter(Field::isChoosable)
                .collect(Collectors.toList());
        return SimpleJsonResponse.build("fields", choosableFields);
    }

    /**
     * "gibt alle Kategorien zurück für die is_subject gesetzt ist und die mit einem Modul aus field verknüpft sind"
     * TODO ???
     * @param id
     * @return
     */
    @GET
    @Path("/{id}/options")
    public Map<String,List<Category>> getOptions(@PathParam("id") Integer id) {
        return null;
    }
}
