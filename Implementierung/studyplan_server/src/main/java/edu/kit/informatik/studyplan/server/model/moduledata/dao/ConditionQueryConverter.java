package edu.kit.informatik.studyplan.server.model.moduledata.dao;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;
import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter for Condition lists to HQL "where"-clauses
 * 
 * @author NiklasUhl
 * 
 * @see Condition
 *
 */
public class ConditionQueryConverter {

	private static String paramPrefix = "param";
	private StringBuilder queryString = new StringBuilder();
	private List<Object> parameters = new ArrayList<Object>();

	/**
	 * 
	 * @param conditions
	 *            a condition list
	 */
	public ConditionQueryConverter(List<Condition> conditions) {
		for (int i = 0; i < conditions.size(); i++) {
			if (i != 0) {
				queryString.append(" and ");
			}
			process(conditions.get(i));
		}
	}

	/**
	 * 
	 * @return returns the HQL "where"-clause with parameter placeholders
	 */
	public String getQueryString() {
		return queryString.toString();
	}

	/**
	 * Loads the parameters to the query
	 * 
	 * @param query
	 *            the query generated with the query string
	 */
	public void setParameters(Query<Module> query) {
		for (int i = 0; i < parameters.size(); i++) {
			query.setParameter(paramPrefix + i, parameters.get(i));
		}
	}

	private void process(Condition condition) {
		StringBuilder conditionBuilder = new StringBuilder();
		if (condition.getLhsName().equals(ModuleAttributeNames.CATEGORY)) {
			conditionBuilder.append(":" + paramPrefix + parameters.size());
			parameters.add(condition.getRhsValues()[0]);
			conditionBuilder.append(" member of m." + condition.getLhsName());
		} else {
			switch (condition.getRelation()) {
			case BETWEEN:
				Object value1 = condition.getRhsValues()[0];
				Object value2 = condition.getRhsValues()[1];
				conditionBuilder.append("m." + condition.getLhsName());
				conditionBuilder.append(" between ");
				conditionBuilder.append(":" + paramPrefix + parameters.size() + " and ");
				parameters.add(value1);
				conditionBuilder.append(":" + paramPrefix + parameters.size());
				parameters.add(value2);
				break;
			case CONTAINS:
				conditionBuilder.append("m." + condition.getLhsName());
				conditionBuilder.append(" like ");
				conditionBuilder.append(":" + paramPrefix + parameters.size());
				parameters.add("%" + condition.getRhsValues()[0] + "%");
				break;
			case EQUALS:
				if (condition.getRhsValues()[0].getClass() != CycleType.class
						|| condition.getRhsValues()[0] == CycleType.BOTH) {
					conditionBuilder.append("m." + condition.getLhsName());
					conditionBuilder.append(" = ");
					conditionBuilder.append(":" + paramPrefix + parameters.size());
					parameters.add(condition.getRhsValues()[0]);
				} else { //rhs == WT or ST
					conditionBuilder.append("((m." + condition.getLhsName());
					conditionBuilder.append(" = ");
					conditionBuilder.append(":" + paramPrefix + parameters.size() + ")");
					parameters.add(condition.getRhsValues()[0]);
					conditionBuilder.append(" or ");
					conditionBuilder.append("(m." + condition.getLhsName());
					conditionBuilder.append(" = ");
					conditionBuilder.append(":" + paramPrefix + parameters.size() + "))");
					parameters.add(CycleType.BOTH);
				}
				break;
			default:
				break;
			}
		}
		queryString.append(conditionBuilder);
	}

}
