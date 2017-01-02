package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Textsuch-Attribut-Filter.
 */
public abstract class ContainsFilter implements AttributeFilter {
    protected String substring;

    /**
     * Liefert eine Filterbedingung, die das Vorkommen des Substrings im Attributswert fordert.
     * @return die Filterbedingung als jOOQ-Condition-Objekt
     */
    @Override
    public Condition getCondition() {
        return null;
    }

    public abstract FilterDescriptor getDescriptor();

    @Override
    public FilterType getFilterType() {
        return FilterType.CONTAINS;
    }

    /**
     * Liefert den Substring, nach welchem gefiltert werden soll.
     * @return der Substring
     */
    public String getSubstring() {
        return substring;
    }
}
