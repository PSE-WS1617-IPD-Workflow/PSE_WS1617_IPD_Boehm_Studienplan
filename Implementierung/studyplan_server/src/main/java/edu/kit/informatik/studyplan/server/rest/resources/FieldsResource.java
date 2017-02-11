package edu.kit.informatik.studyplan.server.rest.resources;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.dao.AuthorizationContext;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;
import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import com.sun.xml.internal.ws.client.RequestContext;

@Path("/fields")
@AuthorizationNeeded
public class FieldsResource {
    @Inject
    Provider<AuthorizationContext> context;

    private User getUser() {
        return context.get().getUser();
    }

    /**
     *
     * @return all chooseable fields (for generation wizard)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<Field>> getChooseableFields() {
        if (getUser().getDiscipline() == null) {
            throw new UnprocessableEntityException();
        }
        return Utils.withModuleDao(moduleDao -> {
            List<Field> choosableFields = moduleDao.getFields(getUser().getDiscipline())
                    .stream()
                    .filter(Field::isChoosable)
                    .collect(Collectors.toList());
            return SimpleJsonResponse.build("fields", choosableFields);
        });
    }


}
