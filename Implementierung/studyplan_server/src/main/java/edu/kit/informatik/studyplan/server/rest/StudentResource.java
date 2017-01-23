package edu.kit.informatik.studyplan.server.rest;

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

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Diese Klasse repräsentiert dir Student-Resource.
 */
@Path("/student")
public class StudentResource {
	@Context
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
		if (studentInput.getStudent() == null)
			throw new BadRequestException();
		User thisStudent = context.getUser();
        Discipline foundDiscipline = ModuleDaoFactory.getModuleDao()
				.getDisciplineById(studentInput.getStudent().getDiscipline().getDisciplineId());
		thisStudent.setDiscipline(foundDiscipline);
		thisStudent.setStudyStart(studentInput.getStudent().getStudyStart());

		List<ModuleEntry> newPassedModules = studentInput.getStudent().getPassedModules()
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
		thisStudent.getPlans().parallelStream()
				.forEach(plan -> plan.setVerificationState(VerificationState.NOT_VERIFIED));
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
		List<ModuleResource.JsonModule> passedModules = context.getUser().getPassedModules().parallelStream().map(entry -> {
			ModuleResource.JsonModule m = new ModuleResource.JsonModule();
			m.setId(entry.getModule().getIdentifier());
			m.setSemester(entry.getSemester());
			return m;
		}).collect(Collectors.toList());
		JsonStudent result = new JsonStudent(
				context.getUser().getDiscipline(),
				context.getUser().getStudyStart(),
				passedModules,
				null);   //TODO aufräumen
		return new StudentInOut(result);
	}

	/**
	 * DELETE-Anfrage: Löscht den Student.
	 */
	@DELETE
	@JsonView(Views.StudentClass.class)
//	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteStudent(StudentInOut studentInOut) {
//		if (studentInOut.getStudent() == null)
//			throw new BadRequestException();
		try {
//			User requested = new User();
//			requested.setUserId(studentInOut.getStudent().getId());
			UserDaoFactory.getUserDao().deleteUser(context.getUser());
		} catch (Exception ex) { //TODO Exception spec

		}
		return Response.ok().build();
	}

	static class StudentInOut {
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
		@JsonProperty("id")
		private Integer id;
		@JsonProperty("discipline")
		private Discipline discipline;
		@JsonProperty("study-start")
		private Semester studyStart;
		@JsonProperty("passed-modules")
		private List<ModuleResource.JsonModule> passedModules;

		public JsonStudent() {}

		public JsonStudent(Discipline discipline, Semester studyStart, List<ModuleResource.JsonModule> passedModules, Integer id) {
			this.id = id;
			this.setDiscipline(discipline);
			this.setStudyStart(studyStart);
			this.setPassedModules(passedModules);
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
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

		public List<ModuleResource.JsonModule> getPassedModules() {
			return passedModules;
		}

		public void setPassedModules(List<ModuleResource.JsonModule> passedModules) {
			this.passedModules = passedModules;
		}
	}

	public static class Views {
		public static class DisciplineClass extends StudentClass {}
		public static class StudentClass {}
	}
}
