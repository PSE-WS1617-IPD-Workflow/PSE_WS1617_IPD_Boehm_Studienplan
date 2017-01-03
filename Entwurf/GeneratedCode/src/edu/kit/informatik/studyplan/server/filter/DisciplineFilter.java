package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Fachrichtungs-Wahlfilter mit den Fachrichtungen als Wahlmöglichkeiten.
 */
public class DisciplineFilter extends ListFilter {
    /**
     * Erzeugt einen neuen Fachrichtungs-Auswahlfilter mit gegebener festgelegter Auswahl.
     *
     * @param selection die Nummer des ausgewählten Elements
     */
    public DisciplineFilter(int selection) {
        super(selection);
    }

    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.DISCIPLINE;
    }

    @Override
    public String[] getItems() {
        return null;
    }
}
