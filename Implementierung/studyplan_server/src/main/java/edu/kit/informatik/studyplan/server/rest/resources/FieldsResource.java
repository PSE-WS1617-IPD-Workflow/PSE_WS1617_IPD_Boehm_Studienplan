package edu.kit.informatik.studyplan.server.rest.resources;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

//import com.sun.xml.internal.ws.client.RequestContext;

@Path("/fields")
public class FieldsResource {
    
    @Inject Provider<AuthorizationContext> contextProvider;
        
    /**
     *
     * @return all choosable fields (for generation wizard)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @AuthorizationNeeded
    public Map<String, List<Field>> getChoosableFields() {
        if (contextProvider.get().getUser().getDiscipline() == null) {
            throw new UnprocessableEntityException();
        }
        List<Field> choosableFields = ModuleDaoFactory.getModuleDao().getFields(contextProvider.get().getUser().getDiscipline())
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
    @Path("/{id}")
    public Map<String, Field> getField(@PathParam("id") Integer id) {
        Field field = ModuleDaoFactory.getModuleDao().getFieldById(id);
        if (field == null) {
            throw new NotFoundException();
        }
        return SimpleJsonResponse.build("field", field);
    }
}
