package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Turnus-Auswahlfilter mit Filterung nach Winter-,
 * Sommersemester oder beidem als Wahlmöglichkeiten.
 */
public class CycleTypeFilter extends ListFilter {
    /**
     * Erzeugt einen neuen Turnus-Auswahlfilter mit gegebener festgelegter Auswahl.
     *
     * @param selection die Nummer des ausgewählten Elements
     */
    public CycleTypeFilter(int selection) {
        super(selection);
    }

    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.CYCLE_TYPE;
    }

    @Override
    public String[] getItems() {
        return null;
    }

}
