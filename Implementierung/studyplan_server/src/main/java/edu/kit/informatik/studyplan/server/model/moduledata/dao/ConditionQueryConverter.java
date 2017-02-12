package edu.kit.informatik.studyplan.server.model.moduledata.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;

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
				if (condition.getLhsName().equals(ModuleAttributeNames.CREDIT_POINTS)) {
					value1 = ((Integer) value1).doubleValue();
					value2 = ((Integer) value2).doubleValue();
				}
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
				conditionBuilder.append("m. " + condition.getLhsName());
				conditionBuilder.append(" = ");
				conditionBuilder.append(":" + paramPrefix + parameters.size());
				parameters.add(condition.getRhsValues()[0]);
				break;
			default:
				break;
			}
		}
		queryString.append(conditionBuilder);
	}

}
