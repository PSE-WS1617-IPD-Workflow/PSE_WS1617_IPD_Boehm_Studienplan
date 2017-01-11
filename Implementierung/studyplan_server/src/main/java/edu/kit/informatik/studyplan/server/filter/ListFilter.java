package edu.kit.informatik.studyplan.server.filter;

import java.util.List;

/**
 * Repräsentiert einen Listenauswahl-Attribut-Filter.
 */
public abstract class ListFilter implements AttributeFilter {
    /**
     * Die Nummer des ausgewählten Elements.
     */
    protected int selection;

    /**
     * Erzeugt einen neuen Auswahlfilter mit gegebener festgelegter Auswahl.
     * @param selection die Nummer des ausgewählten Elements
     */
    protected ListFilter(int selection) {
        this.selection = selection;
    }

    /**
     * Liefert eine Filterbedingung, die die Übereinstimmung des untersuchten Attributswertes mit der festgelegten
     * Auswahl (selection) fordert.
     * @return die Filterbedingung als jOOQ-Condition-Objekt
     */
    public Condition getCondition() {
        return null;
    }

    public abstract FilterDescriptor getDescriptor();

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
    public abstract List<String> getItems();
}
