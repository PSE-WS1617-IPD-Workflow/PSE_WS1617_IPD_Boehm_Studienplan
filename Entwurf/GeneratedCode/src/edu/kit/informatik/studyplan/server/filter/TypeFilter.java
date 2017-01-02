package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Modultyp-Wahlfilter.
 */
public class TypeFilter extends ListFilter {
    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.TYPE;
    }

    /**
     * Liefert die Modultypen als Wahlmöglichkeiten.
     * @return  die Wahlmöglichkeiten des Auswahlfilters
     */
    @Override
    public String[] getItems() {
        return new String[0];
    }
}
