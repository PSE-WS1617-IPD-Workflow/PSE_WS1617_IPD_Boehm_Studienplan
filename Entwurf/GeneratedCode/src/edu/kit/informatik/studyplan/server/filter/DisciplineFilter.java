package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Fachrichtungs-Wahlfilter.
 */
public class DisciplineFilter extends ListFilter {
    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.DISCIPLINE;
    }

    /**
     * Liefert die Fachrichtungen als Wahlmgölichkeiten.
     * @return die Wahlmöglichkeiten des Auswahlfilters
     */
    @Override
    public String[] getItems() {
        return new String[0];
    }
}
