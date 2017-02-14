package edu.kit.informatik.studyplan.server.verification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.RuleGroup;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.userdata.VerificationState;
import edu.kit.informatik.studyplan.server.rest.resources.json.JsonModule;

/**
 * result object of a verification processs
 * @author NiklasUhl
 */
public class VerificationResult {
	
	@JsonProperty("violations")
	private Collection<ModuleConstraint> violations;
	@JsonIgnore
	private Collection<Field> fieldViolations;
	@JsonProperty("rule-group-violations")
	private Collection<RuleGroup> ruleGroupViolations;
	@JsonIgnore
	private Collection<Module> compulsoryViolations;
	@JsonIgnore
	private boolean correct;
	
	/**
	 * constructs a new empty result which is set to invalid
	 */
	public VerificationResult() {
		violations = new HashSet<ModuleConstraint>();
		fieldViolations = new ArrayList<Field>();
		ruleGroupViolations = new ArrayList<RuleGroup>();
		compulsoryViolations = new ArrayList<Module>();
	}

	/**
	 * 
	 * @return the violated constraints
	 */
	public Collection<ModuleConstraint> getViolations() {
		return violations;
	}
	
	/**
	 * 
	 * @return the fields whose conditions are violated
	 */
	public Collection<Field> getFieldViolations() {
		return fieldViolations;
	}
	
	/**
	 * 
	 * @return the rule groups whose conditions are violated
	 */
	public Collection<RuleGroup> getRuleGroupViolations() {
		return ruleGroupViolations;
	}

	/**
	 * @return the compulsory modules which aren't contained in the plan
	 */
	public Collection<Module> getCompulsoryViolations() {
		return compulsoryViolations;
	}

	/**
	 * 
	 * @return if the the verification returned valid
	 */
	public boolean isCorrect() {
		return correct;
	}
	
	/**
	 * 
	 * @param isCorrect sets if the verification result returns valid
	 */
	public void setCorrect(boolean isCorrect) {
		correct = isCorrect;
	}
	
	/**
	 * 
	 * @return the {@link VerificationState} for this result
	 */
	@JsonProperty("status")
	public VerificationState getVerificationState() {
		return isCorrect() ? VerificationState.VALID : VerificationState.INVALID;
	}
	
	/**
	 * 
	 * @return a json-serializable representation of compulsory violations
	 */
	@JsonProperty("compulsory-violations")
	public List<JsonModule> getCompulsoryViolationsForJson() {
		return getCompulsoryViolations().stream()
				.map(module -> {
					JsonModule jsonModule = new JsonModule();
					jsonModule.setName(module.getName());
					return jsonModule;
				}).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return a json-serializable representation of field violations
	 */
	@JsonProperty("field-violations")
	public List<FieldSummaryDto> getFieldViolationsForJson() {
		return getFieldViolations().stream()
				.map(field -> new FieldSummaryDto(field))
				.collect(Collectors.toList());
	}
	
	
	/**
	 * DataTransferObject for field, only with name an minimum credit points
	 * @author NiklasUhl
	 *
	 */
	public class FieldSummaryDto {
		
		@JsonProperty
		String name;
		
		@JsonProperty("min-ects")
		double minEcts;
		
		/**
		 * constructs the DTO from the given field
		 * @param field the field
		 */
		public FieldSummaryDto(Field field) {
			this.name = field.getName();
			this.minEcts = field.getMinEcts();
		}
	}
};
