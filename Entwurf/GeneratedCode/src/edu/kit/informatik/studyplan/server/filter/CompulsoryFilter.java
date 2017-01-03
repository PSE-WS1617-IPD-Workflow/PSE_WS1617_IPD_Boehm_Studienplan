package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Pflicht-/Wahlmodul-Auswahlfilter mit Filterung nach
 * Pflicht-, Wahlmodulen oder beidem als Wahlmöglichkeiten.
 */
public class CompulsoryFilter extends ListFilter {
    /**
     * Erzeugt einen neuen Pflicht-/Wahlmodul-Auswahlfilter mit gegebener festgelegter Auswahl.
     *
     * @param selection die Nummer des ausgewählten Elements
     */
    public CompulsoryFilter(int selection) {
        super(selection);
    }

    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.COMPULSORY;
    }

    @Override
    public String[] getItems() {
        return null;
    }
}
