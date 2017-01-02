package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Pflicht-/Wahlmodul-Auswahlfilter.
 */
public class CompulsoryFilter extends ListFilter {
    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.COMPULSORY;
    }

    /**
     * Liefert Filterung nach Pflicht-, Wahlmodulen oder beidem als Wahlmöglichkeiten.
     *
     * @return die Wahlmöglichkeiten des Auswahl-Filters
     */
    @Override
    public String[] getItems() {
        return new String[0];
    }
}
