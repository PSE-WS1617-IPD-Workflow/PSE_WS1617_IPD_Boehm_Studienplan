package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Listenauswahl-Attribut-Filter.
 */
public abstract class ListFilter implements AttributeFilter {
    protected int selection;
    /**
     * Liefert eine Filterbedingung, die die Übereinstimmung des untersuchten Attributswertes mit dem festgelegten
     * Wahlelement (selection) fordert.
     * @return die Filterbedingung als jOOQ-Condition-Objekt
     */
    @Override
    public Condition getCondition() {
        return null;
    }

    public abstract FilterDescriptor getDescriptor();

    @Override
    public FilterType getFilterType() {
        return FilterType.LIST;
    }

    /**
     * Liefert die Nummer des selektierten Elements.
     * @return die Element-Nummer
     */
    public int getSelection() {
        return selection;
    }

    /**
     * Liefert alle Wahlmöglichkeiten dieses Auswahl-Filters als Strings.
     * @return die Wahlmöglichkeiten des Auswahl-Filters
     */
    public abstract String[] getItems();
}
