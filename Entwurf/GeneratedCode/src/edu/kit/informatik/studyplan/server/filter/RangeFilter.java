package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Intervall-Beschränkungs-Filter für ganzzahlige Attribute.
 */
public abstract class RangeFilter implements AttributeFilter {
    protected int lower;
    protected int upper;
    protected int min;
    protected int max;
    /**
     * Liefert eine Filterbedingung, die vom Attributswert das Einhalten der festgelegten Intervall-Grenzen fordert.
     * @return die Filterbedingung als jOOQ-Condition-Objekt
     */
    @Override
    public Condition getCondition() {
        return null;
    }

    public abstract FilterDescriptor getDescriptor();

    @Override
    public FilterType getFilterType() {
        return FilterType.RANGE;
    }

    /**
     * Liefert die festgelegte untere Schranke des Filters.
     * @return die untere Schranke
     */
    public int getLower() {
        return lower;
    }

    /**
     * Liefert die festgelegte obere Schranke des Filters.
     * @return die obere Schranke
     */
    public int getUpper() {
        return upper;
    }

    /**
     * Liefert den Mindestwert der unteren Schranke des Filters.
     * @return der Mindestwert der unteren Schranke
     */
    public int getMin() {
        return min;
    }

    /**
     * Liefert den Maximalwert der oberen Schranke des Filters.
     * @return den Maximalwert der oberen Schranke
     */
    public int getMax() {
        return max;
    }
}
