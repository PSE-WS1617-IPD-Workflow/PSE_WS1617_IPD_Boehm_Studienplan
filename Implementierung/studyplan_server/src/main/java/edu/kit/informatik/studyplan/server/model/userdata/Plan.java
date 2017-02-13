package edu.kit.informatik.studyplan.server.model.userdata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.rest.resources.json.JsonModule;

/**
 * Class modeling a studyplan
 */
@Entity
@Table(name = "plan")
public class Plan {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid-gen")
	@Column(name = "identifier")
	@JsonProperty("id")
	private String identifier;

	@Column(name = "name")
	@JsonProperty("name")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	@JsonProperty("status")
	private VerificationState state;

	@Transient
	@JsonProperty("creditpoints-sum")
	private double creditPoints = -1;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name = "plan_entries", 
		joinColumns = @JoinColumn(name = "plan_identifier"), 
		inverseJoinColumns = @JoinColumn(name = "entry_id"))
	@JsonIgnore
	private List<ModuleEntry> moduleEntries = new LinkedList<ModuleEntry>();

	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ModulePreference> modulePreferences = new LinkedList<ModulePreference>();

	/**
	 * Returns the preference for a given module. Returns <code>null</code> if
	 * no preference exists.
	 *
	 * @return the {@link PreferenceType}
	 * @param module
	 *            the module to search for
	 */
	@JsonIgnore
	public PreferenceType getPreferenceForModule(Module module) {
		return modulePreferences.stream().filter(preference -> preference.getModule().equals(module))
				.map(ModulePreference::getPreference).findFirst().orElse(null);
	}

	/**
	 *
	 * @return returns the unique plan identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 *
	 * @param identifier
	 *            the plan identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 *
	 * @return returns the plan name
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 * @return returns the plans {@link VerificationState}
	 */
	public VerificationState getVerificationState() {
		return state;
	}

	/**
	 *
	 * @param verificationState
	 *            the verification state to set
	 */
	public void setVerificationState(VerificationState verificationState) {
		this.state = verificationState;
	}

	/**
	 *
	 * @return returns the total credit point sum of this plan
	 */
	public double getCreditPoints() {
		if (creditPoints == -1) {
			creditPoints = getAllModuleEntries().stream().mapToDouble(entry -> entry.getModule().getCreditPoints()).sum();
		}
		return creditPoints;
	}

	/**
	 *
	 * @return returns the Owner of this plan
	 */
	public User getUser() {
		return user;
	}

	/**
	 *
	 * @param user
	 *            sets the owner
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 *
	 * @return returns a list of all plan entries (excluding passed modules)
	 */
	public List<ModuleEntry> getModuleEntries() {
		return moduleEntries;
	}

	/**
	 * 
	 * @return returns a list of all module preferences
	 */
	@JsonIgnore
	public List<ModulePreference> getPreferences() {
		return modulePreferences;
	}

	/**
	 * Only being called by Jackson and/or REST handlers, respectively.
	 * 
	 * @return Publishes the module entries inside the plan's JSON
	 *         representation.
	 */
	@JsonProperty("modules")
	public List<JsonModule> getJsonModules() {
		return getModuleEntries().stream().map(entry -> {
			JsonModule jsonModule = new JsonModule();
			jsonModule.setId(entry.getModule().getIdentifier());
			jsonModule.setName(entry.getModule().getName());
			jsonModule.setSemester(entry.getSemester());
			jsonModule.setCreditPoints(entry.getModule().getCreditPoints());
			jsonModule.setLecturer(entry.getModule().getModuleDescription().getLecturer());
			jsonModule.setCycleType(entry.getModule().getCycleType());
			jsonModule.setPreference(getPreferenceForModule(entry.getModule()));
			return jsonModule;
		}).collect(Collectors.toList());
	}

	/**
	 * Only being called by Jackson and/or REST handlers, respectively.
	 * 
	 * @param jsonModules
	 *            The modules attribute's content from the plan's JSON
	 *            representation.
	 */
	@JsonProperty("modules")
	public void setJsonModules(List<JsonModule> jsonModules) {
		HashSet<String> placedModulesIds = new HashSet<>(jsonModules.size()); // for
																				// finding
																				// duplicates
		List<ModuleEntry> moduleEntries = new ArrayList<>(jsonModules.size());
		List<ModulePreference> preferences = new LinkedList<>();
		for (JsonModule jsonModule : jsonModules) {
			if (placedModulesIds.contains(jsonModule.getId())) {
				throw new BadRequestException();
			} else {
				placedModulesIds.add(jsonModule.getId());
			}
			// if (jsonModule.getSemester() <
			// user.getStudyStart().getDistanceToCurrentSemester()) {
			// throw new BadRequestException();
			// }
			Module module = Utils.withModuleDao(dao -> dao.getModuleById(jsonModule.getId()));
			if (module == null) {
				throw new NotFoundException();
			}
			ModuleEntry entry = new ModuleEntry(module, jsonModule.getSemester());
			moduleEntries.add(entry);
			if (jsonModule.getPreference() != null) {
				ModulePreference preference = new ModulePreference(module, jsonModule.getPreference(), this);
				preferences.add(preference);
			}
		}
		this.moduleEntries = moduleEntries;
		this.modulePreferences = preferences;
	}

	/**
	 * Checks if the plan contains the given module (including passed modules).
	 * 
	 * @param module
	 *            the module
	 * @return the result
	 */
	public boolean contains(Module module) {
		return getAllModuleEntries().stream().anyMatch(entry -> entry.getModule().equals(module));
	}

	/**
	 * Searches for the module entry for the module in this plan (including
	 * passed modules).
	 * 
	 * @param module
	 *            the module
	 * @return returns the module entry or <code>null</code> if not found
	 */
	@Transient
	@JsonIgnore
	public ModuleEntry getEntryFor(Module module) {
		return getAllModuleEntries().stream().filter(entry -> entry.getModule().equals(module)).findFirst()
				.orElse(null);
	}

	/**
	 * @return returns a list of all module entries for this plan (including
	 *         passed modules).
	 */
	@Transient
	@JsonIgnore
	public List<ModuleEntry> getAllModuleEntries() {
		LinkedList<ModuleEntry> allEntries = new LinkedList<ModuleEntry>(moduleEntries);
		if (user != null){
			allEntries.addAll(user.getPassedModules());
		}
		return allEntries;
	}
};
