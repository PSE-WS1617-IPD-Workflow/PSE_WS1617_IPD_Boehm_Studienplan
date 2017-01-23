package edu.kit.informatik.studyplan.server.rest;

import com.fasterxml.jackson.annotation.JsonView;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/disciplines")
public class DisciplinesResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(StudentResource.Views.DisciplineClass.class)
    public Map<String, List<Discipline>> getAllDisciplines() {
        return SimpleJsonResponse.build("disciplines", ModuleDaoFactory.getModuleDao().getDisciplines());
    }
}
