package edu.kit.informatik.studyplan.server.verification.standard;

import java.util.HashMap;
import java.util.List;

import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.RuleGroup;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleOrientation;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.verification.VerificationResult;
import edu.kit.informatik.studyplan.server.verification.Verifier;

/************************************************************/
/**
 * Concrete verifier implementation
 */
public class StandardVerifier implements Verifier {

	private VerificationResult result;

	@Override
	public VerificationResult verify(Plan plan) {
		result = new VerificationResult();
		result.setCorrect(true);
		findCompulsoryViolations(plan);
		findFieldViolations(plan);
		findRuleGroupViolations(plan);
		findConstraintViolations(plan);
		return result;
	}

	private void findCompulsoryViolations(Plan plan) {
		List<Module> compulsoryModules = plan.getUser().getDiscipline().getCompulsoryModules();
		for (Module module : compulsoryModules) {
			if (!plan.contains(module)) {
				result.getCompulsoryViolations().add(module);
				result.setCorrect(false);
			}
		}
	}

	private void findFieldViolations(Plan plan) {
		HashMap<Field, Double> fieldMap = new HashMap<Field, Double>();
		// get total credit points per field
		for (ModuleEntry entry : plan.getAllModuleEntries()) {
			Field field = entry.getModule().getField();
			if (!fieldMap.containsKey(field)) {
				fieldMap.put(field, entry.getModule().getCreditPoints());
			} else {
				double sum = fieldMap.get(field);
				fieldMap.put(field, sum + entry.getModule().getCreditPoints());
			}
		}
		// field is violated if the actual credit point sum is less than the
		// minimum value
		for (Field field : plan.getUser().getDiscipline().getFields()) {
			double minEcts = field.getMinEcts();
			double actualEcts = fieldMap.getOrDefault(field, 0.0);
			if (actualEcts < minEcts) {
				result.getFieldViolations().add(field);
				result.setCorrect(false);
			}
		}
	}

	private void findRuleGroupViolations(Plan plan) {
		List<RuleGroup> ruleGroups = plan.getUser().getDiscipline().getRuleGroups();
		int[] numberOfModules = new int[ruleGroups.size()];
		for (int i = 0; i < ruleGroups.size(); i++) {
			List<Module> modules = ruleGroups.get(i).getModules();
			for (Module module : modules) {
				if (plan.contains(module)) {
					numberOfModules[i]++;
				}
			}
		}
		for (int i = 0; i < ruleGroups.size(); i++) {
			RuleGroup group = ruleGroups.get(i);
			if (!isInRange(numberOfModules[i], group)) {
				result.getRuleGroupViolations().add(group);
				result.setCorrect(false);
			}
		}
	}

	private void findConstraintViolations(Plan plan) {
		for (ModuleEntry moduleEntry : plan.getAllModuleEntries()) {
			for (ModuleConstraint constraint : moduleEntry.getModule().getConstraints()) {
				ModuleOrientation orientation;
//				if (constraint.getFirstModule().equals(moduleEntry.getModule())) {
//					orientation = ModuleOrientation.LEFT_TO_RIGHT;
//				} else {
//					orientation = ModuleOrientation.RIGHT_TO_LEFT;
//				}
				ModuleEntry firstEntry = plan.getEntryFor(constraint.getFirstModule());
				ModuleEntry secondEntry = plan.getEntryFor(constraint.getSecondModule());
				if (firstEntry != null) {
					firstEntry.setPassed(plan.getUser().getPassedModules().contains(firstEntry));
				}
				if (secondEntry != null) {
					secondEntry.setPassed(plan.getUser().getPassedModules().contains(secondEntry));
				}
				if (!constraint.getConstraintType().isValid(firstEntry, secondEntry, ModuleOrientation.LEFT_TO_RIGHT)) {
					result.getViolations().add(constraint);
					result.setCorrect(false);
				}
			}
		}
	}

	private boolean isInRange(int numberOfModules, RuleGroup ruleGroup) {
		int minNum = ruleGroup.getMinNum();
		int maxNum = ruleGroup.getMaxNum();
		if (minNum == -1) {
			if (maxNum == -1) {
				return true;
			} else {
				return numberOfModules <= maxNum;
			}
		} else {
			if (maxNum == -1) {
				return numberOfModules >= minNum;
			} else {
				return numberOfModules >= minNum && numberOfModules <= maxNum;
			}
		}
	}
};
