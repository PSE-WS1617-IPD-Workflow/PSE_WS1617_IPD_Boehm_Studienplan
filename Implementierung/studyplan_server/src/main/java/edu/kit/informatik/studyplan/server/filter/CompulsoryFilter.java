package edu.kit.informatik.studyplan.server.filter;

import java.util.List;

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
    public List<String> getItems() {
        return null;
    }
}
