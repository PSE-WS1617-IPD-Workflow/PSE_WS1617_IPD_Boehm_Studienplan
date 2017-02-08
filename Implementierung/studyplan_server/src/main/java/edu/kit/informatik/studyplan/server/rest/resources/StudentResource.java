package edu.kit.informatik.studyplan.server.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Semester;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.VerificationState;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDaoFactory;
import edu.kit.informatik.studyplan.server.rest.UnprocessableEntityException;
import edu.kit.informatik.studyplan.server.rest.resources.json.JsonModule;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Diese Klasse repräsentiert dir Student-Resource.
 */
@Path("/student")
public class StudentResource {
	@Inject
	AuthorizationContext context;

	/**
	 * PUT-Anfrage: Ersetzt Informationen über einen Student und löscht die
	 * Verifikationsinformationen.
	 * 
	 *
	 * @return Student mit neue Informationen als JSON Objekt.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(Views.StudentClass.class)
	public StudentInOut replaceInformation(StudentInOut studentInput) {
		User thisStudent = context.getUser();
		JsonStudent jsonStudent = studentInput.getStudent();
		if (jsonStudent.getDiscipline() != null) {
			Discipline foundDiscipline = ModuleDaoFactory.getModuleDao()
					.getDisciplineById(jsonStudent.getDiscipline().getDisciplineId());
			thisStudent.setDiscipline(foundDiscipline);
		}
        if (jsonStudent.getStudyStart() != null) {
			thisStudent.setStudyStart(jsonStudent.getStudyStart());
		}
		if (jsonStudent.getPassedModules() != null) {
			List<ModuleEntry> newPassedModules = jsonStudent.getPassedModules()
					.parallelStream()
					.map(jsonModule -> {
						ModuleEntry entry = new ModuleEntry();
						entry.setModule(ModuleDaoFactory.getModuleDao().getModuleById(jsonModule.getId()));
						if (entry.getModule() == null)
							throw new BadRequestException();
						if (jsonModule.getSemester() == null)
							throw new BadRequestException();
						if (jsonModule.getSemester() >= thisStudent.getStudyStart().getDistanceToCurrentSemester())
							throw new BadRequestException();
						entry.setSemester(jsonModule.getSemester());
						return entry;
					})
					.collect(Collectors.toList());

			thisStudent.getPassedModules().clear();
			thisStudent.getPassedModules().addAll(newPassedModules);
		}

		if (jsonStudent.getDiscipline() != null || jsonStudent.getPassedModules() != null) {
			thisStudent.getPlans().parallelStream()
					.forEach(plan -> plan.setVerificationState(VerificationState.NOT_VERIFIED));
		}
		UserDaoFactory.getUserDao().updateUser(thisStudent);
		return studentInput; //hasn't changed
	}



	/**
	 * GET-Anfrage: Gibt die Studentinformationen zurück.
	 * 
	 * @return die Studentinformationen als JSON Objekt.
	 */
	@GET
	@JsonView(Views.StudentClass.class)
	@Produces(MediaType.APPLICATION_JSON)
	public StudentInOut getInformation() {
		List<JsonModule> passedModules = context.getUser().getPassedModules().parallelStream().map(entry -> {
			JsonModule m = new JsonModule();
			m.setId(entry.getModule().getIdentifier());
			m.setSemester(entry.getSemester());
			return m;
		}).collect(Collectors.toList());
		JsonStudent result = new JsonStudent(
				context.getUser().getDiscipline(),
				context.getUser().getStudyStart(),
				passedModules);   //TODO aufräumen
		return new StudentInOut(result);
	}

	/**
	 * DELETE-Anfrage: Löscht den Student.
	 */
	@DELETE
	@JsonView(Views.StudentClass.class)
	public Response deleteStudent() {
		if (UserDaoFactory.getUserDao().findUser(context.getUser()) == null)
			throw new UnprocessableEntityException();
		UserDaoFactory.getUserDao().deleteUser(context.getUser());
		return Response.ok().build();
	}

	static class StudentInOut {
		@NotNull
		private JsonStudent student;

		public StudentInOut() {}

		public StudentInOut(JsonStudent student) {
			this.student = student;
		}

		public JsonStudent getStudent() {
			return student;
		}

		public void setStudent(JsonStudent student) {
			this.student = student;
		}
	}

	public static class JsonStudent {
		@JsonProperty("discipline")
		private Discipline discipline;
		@JsonProperty("study-start")
		private Semester studyStart;
		@JsonProperty("passed-modules")
		private List<JsonModule> passedModules;

		public JsonStudent() {}

		public JsonStudent(Discipline discipline, Semester studyStart, List<JsonModule> passedModules) {
			this.setDiscipline(discipline);
			this.setStudyStart(studyStart);
			this.setPassedModules(passedModules);
		}

		public Discipline getDiscipline() {
			return discipline;
		}

		public void setDiscipline(Discipline discipline) {
			this.discipline = discipline;
		}

		public Semester getStudyStart() {
			return studyStart;
		}

		public void setStudyStart(Semester studyStart) {
			this.studyStart = studyStart;
		}

		public List<JsonModule> getPassedModules() {
			return passedModules;
		}

		public void setPassedModules(List<JsonModule> passedModules) {
			this.passedModules = passedModules;
		}
	}

	public static class Views {
		public static class DisciplineClass extends StudentClass {}
		public static class StudentClass {}
	}
}
