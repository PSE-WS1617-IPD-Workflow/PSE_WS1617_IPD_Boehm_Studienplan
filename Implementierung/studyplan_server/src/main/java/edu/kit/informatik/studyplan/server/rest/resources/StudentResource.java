package edu.kit.informatik.studyplan.server.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Semester;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.VerificationState;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;
import edu.kit.informatik.studyplan.server.rest.resources.json.JsonModule;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST resource for /student.
 */
@Path("/student")
@AuthorizationNeeded
public class StudentResource {
	@Inject
	Provider<AuthorizationContext> context;

	private User getUser() {
		return context.get().getUser();
	}

	/**
	 * PUT request handler.
	 * Replaces the student's information with the given. All plans of the user are being flagged NOT_VERIFIED and
	 * if passed-modules is set, these modules are removed from all plans in which they occur.
	 *
	 * @param studentInput the given student's information.
	 * @return studentInput
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JsonView(Views.StudentClass.class)
	public StudentInOut replaceInformation(StudentInOut studentInput) {
		return Utils.withUserDao(userDao -> Utils.withModuleDao(moduleDao -> Utils.withPlanDao(planDao -> {
			User thisStudent = getUser();
			JsonStudent jsonStudent = studentInput.getStudent();
			if (jsonStudent.getDiscipline() != null) {
				Discipline foundDiscipline = moduleDao
						.getDisciplineById(jsonStudent.getDiscipline().getDisciplineId());
				if (foundDiscipline == null) {
					throw new NotFoundException();
				}
				thisStudent.setDiscipline(foundDiscipline);
			}
			if (jsonStudent.getStudyStart() != null) {
				thisStudent.setStudyStart(jsonStudent.getStudyStart());
			}
			if (jsonStudent.getPassedModules() != null) {
				List<ModuleEntry> newPassedModules = jsonStudent.getPassedModules()
						.stream()
						.map(jsonModule -> {
							ModuleEntry entry =
									new ModuleEntry(moduleDao.getModuleById(jsonModule.getId()),
											jsonModule.getSemester());
							if (entry.getModule() == null) {
								throw new BadRequestException();
							}
							if (jsonModule.getSemester() == null) {
								throw new BadRequestException();
							}
							if (jsonModule.getSemester() > thisStudent.getStudyStart().getDistanceToCurrentSemester()) {
								throw new BadRequestException();
							}
							return entry;
						})
						.collect(Collectors.toList());

				thisStudent.getPassedModules().clear();
				thisStudent.getPassedModules().addAll(newPassedModules);
			}

			if (jsonStudent.getDiscipline() != null || jsonStudent.getPassedModules() != null) {
				Set<Module> passedModuleSet = thisStudent.getPassedModules().stream()
						.map(ModuleEntry::getModule)
						.collect(Collectors.toSet());

				thisStudent.getPlans().forEach(plan -> {
					plan.getModuleEntries().removeIf(entry -> passedModuleSet.contains(entry.getModule()));
					plan.setVerificationState(VerificationState.NOT_VERIFIED);
				});
			}
			userDao.updateUser(thisStudent);
			return getInformation();
		})));
	}



	/**
	 * GET request handler.
	 * 
	 * @return the student's information as JSON.
	 */
	@GET
	@JsonView(Views.StudentClass.class)
	@Produces(MediaType.APPLICATION_JSON)
	public StudentInOut getInformation() {
		List<JsonModule> passedModules = getUser().getPassedModules().stream().map(entry -> {
			JsonModule m = new JsonModule();
			m.setId(entry.getModule().getIdentifier());
			m.setName(entry.getModule().getName());
			m.setCreditPoints(entry.getModule().getCreditPoints());
			m.setLecturer(entry.getModule().getModuleDescription().getLecturer());
			m.setSemester(entry.getSemester());
			return m;
		}).collect(Collectors.toList());
		int distance;
		if (getUser().getStudyStart() == null) {
			distance = 0;
		} else {
			distance = getUser().getStudyStart().getDistanceToCurrentSemester();
		}
		JsonStudent result = new JsonStudent(
				getUser().getDiscipline(),
				getUser().getStudyStart(),
				passedModules, distance);
		return new StudentInOut(result);
	}

	/**
	 * DELETE reuest handler.
	 *
	 * Deletes the student from the database.
	 * @return OK 200 if successful.
	 */
	@DELETE
	@JsonView(Views.StudentClass.class)
	public Response deleteStudent() {
		return Utils.withUserDao(dao -> {
			dao.deleteUser(getUser());
			return Response.ok().build();
		});
	}

	/**
	 * Class for encapsulating a JsonStudent instance.
     */
	static class StudentInOut {
		@NotNull
		private JsonStudent student;

		/**
		 * Empty constructor.
         */
		public StudentInOut() { }

		/**
		 * JsonStudent initializer.
		 * @param student the JsonStudent
         */
		public StudentInOut(JsonStudent student) {
			this.student = student;
		}

		/**
		 *
		 * @return the JsonStudent
         */
		public JsonStudent getStudent() {
			return student;
		}

		/**
		 * Sets the JsonStudent
		 * @param student the JsonStudent
         */
		public void setStudent(JsonStudent student) {
			this.student = student;
		}
	}

	/**
	 * JSON representation of a student's data. The attributes are already described inside the User class and therefore
	 * left mostly undocumented.
     */
	public static class JsonStudent {
		@JsonProperty("discipline")
		private Discipline discipline;
		@JsonProperty("study-start")
		private Semester studyStart;
		@JsonProperty("passed-modules")
		private List<JsonModule> passedModules;
		@JsonProperty("current-semester")
		private Integer currentSemester;

		/**
		 * Empty constructor.
         */
		public JsonStudent() { }

		/**
		 * Creates a new JsonStudent with given parameters.
		 * @param discipline discipline
		 * @param studyStart studyStart
		 * @param passedModules passedModules
         * @param currentSemester currentSemester
         */
		public JsonStudent(Discipline discipline, Semester studyStart, List<JsonModule> passedModules,
						   Integer currentSemester) {
			this.currentSemester = currentSemester;
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

		public Integer getCurrentSemester() {
			return currentSemester;
		}

		public void setCurrentSemester(Integer currentSemester) {
			this.currentSemester = currentSemester;
		}
	}

	/**
	 * Internal class for JSON serialization of {@link Discipline}.
	 * Controls JSON visibility of id and name attributes.
     */
	public static class Views {
		/**
		 * Denotes the /disciplines handler's view.
         */
		public static class DisciplineClass extends StudentClass { }
		/**
		 * Denotes the /student handler's view.
         */
		public static class StudentClass { }
	}
}
