package edu.kit.informatik.studyplan.server.filter.condition;

import java.util.Arrays;

/**
 * Represents a simple one-relational attribute-based boolean condition.
 */
public class Condition {  //DO NOT MAKE THIS FINAL, OTHERWISE SOME TESTS WON'T COMPILE!
    private String lhsName;
    private Object[] rhsValues;
    private BooleanRelation relation;

    private Condition(String lhsName, Object[] rhsValues, BooleanRelation relation) {
        this.lhsName = lhsName;
        this.rhsValues = rhsValues;
        this.relation = relation;
    }

    /**
     * @return the attribute name of the attribute on the relation's left-hand side.
     */
    public String getLhsName() {
        return lhsName;
    }

    /**
     * @see BooleanRelation
     * @return the value objects of the "right-hand side" of the relation.
     *
     */
    public Object[] getRhsValues() {
        return rhsValues;
    }

    /**
     * @return the relation type.
     */
    public BooleanRelation getRelation() {
        return relation;
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", lhsName, relation, Arrays.toString(rhsValues));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Condition) &&
                this.getLhsName().equals(((Condition) obj).getLhsName()) &&
                this.getRelation().equals(((Condition) obj).getRelation()) &&
                Arrays.equals(this.getRhsValues(), ((Condition) obj).getRhsValues());
    }

    /**
     * Creates an `Equals` condition.
     * @param lhsName the attribute name of the left-hand side. Must not be null.
     * @param rhsValue the value object of the right-hand side. Must not be null.
     * @return the condition
     */
    public static Condition createEquals(String lhsName, Object rhsValue) {
        if (lhsName == null || rhsValue == null) {
            throw new IllegalArgumentException();
        }
        return new Condition(lhsName, new Object[]{rhsValue}, BooleanRelation.EQUALS);
    }

    /**
     * Creates a `Between` condition. lower must be <= greater.
     * @param lhsName the attribute name of the left-hand side. Must not be null.
     * @param lower the lower bound
     * @param upper the upper bound
     * @return the condition
     */
    public static Condition createBetween(String lhsName, double lower, double upper) {
        if (lower > upper || lhsName == null) {
            throw new IllegalArgumentException();
        }
        return new Condition(lhsName, new Object[]{lower, upper}, BooleanRelation.BETWEEN);
    }

    /**
     * Creates a `Contains` condition.
     * @param lhsName the attribute name of the left-hand side. Must not be null.
     * @param rhsSubstring the substring on the right-hand side. Must not be null.
     * @return the condition
     */
    public static Condition createContains(String lhsName, String rhsSubstring) {
        if (lhsName == null || rhsSubstring == null) {
            throw new IllegalArgumentException();
        }
        return new Condition(lhsName, new Object[]{rhsSubstring}, BooleanRelation.CONTAINS);
    }
}
