package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.Filter;

/**
 * Bündelt mehrere Filter zu einem einzigen mittels UND-Verknüpfung der Filterbedingungen.
 */
public class MultiFilter implements Filter {
    private Filter[] filters;

    /**
     * Erzeugt einen neuen MultiFilter aus gegebenen Unterfiltern.
     * @param filters die Unterfilter, die gebündelt werden sollen
     */
    public MultiFilter(Filter[] filters) {
        this.filters = filters;
    }

    /**
     * Gibt alle Filter zurück, die von diesem MultiFilter gebündelt werden.
     * @return die gebündelten Filter
     */
    public Filter[] getFilters() {
        return filters;
    }

    /**
     * Gibt die UND-Verknüpfung der Filterbedingungen der gebündelten Filter
     * als Filterbedingung zurück, oder eine konstant wahre Filterbedingung,
     * falls filters leer ist.
     * @return Die neue Filterbedingung als jOOQ-Condition-Objekt
     */
    @Override
    public Condition getCondition() {
        return null;
    }
}