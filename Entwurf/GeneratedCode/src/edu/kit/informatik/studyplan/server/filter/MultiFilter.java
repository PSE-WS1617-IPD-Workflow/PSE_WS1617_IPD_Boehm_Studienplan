package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.Filter;

/**
 * Bündelt mehrere Filter zu einem mittels AND-Verknüpfung der Filterbedingungen.
 */
public class MultiFilter implements Filter {
    /**
     * Gibt alle Filter zurück, die von diesem MultiFilter gebündelt werden.
     * @return die gebündelten Filter
     */
    public Filter[] getFilters() {
        return filters;
    }

    private Filter[] filters;

    /**
     * Gibt die UND-Verknüpfung der Filterbedingungen der gebündelten Filter
     * als Filterbedingung zurück.
     * @return Die neue Filterbedingung als jOOQ-Condition-Objekt
     */
    @Override
    public Condition getCondition() {
        return null;
    }
}