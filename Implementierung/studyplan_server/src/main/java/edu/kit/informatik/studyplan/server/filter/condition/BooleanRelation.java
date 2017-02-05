package edu.kit.informatik.studyplan.server.filter.condition;

/**
 * Representations of boolean relation types.
 */
public enum BooleanRelation {
    /**
     * Represents the equality relation.
     * The right-hand side of an equals condition should contain only one element, the object to compare to.
     */
    EQUALS,
    /**
     * Represents the between relation.
     * The right-hand side of a between condition should contain two elements: the lower bound and the upper bound,
     * both Integers.
     */
    BETWEEN,
    /**
     * Represents the contains relation.
     * The right-hand side of a contains condition should contain only one element, the required substring.
     */
    CONTAINS
}
