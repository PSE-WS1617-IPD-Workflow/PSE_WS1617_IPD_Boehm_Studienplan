package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Modultyp-Wahlfilter mit den Modultypen als Wahlmöglichkeiten.
 */
public class TypeFilter extends ListFilter {
    /**
     * Erzeugt einen neuen Modultyp-Wahlfilter mit gegebener festgelegter Auswahl.
     *
     * @param selection die Nummer des ausgewählten Elements
     */
    public TypeFilter(int selection) {
        super(selection);
    }

    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.TYPE;
    }

    @Override
    public String[] getItems() {
        return null;
    }
}