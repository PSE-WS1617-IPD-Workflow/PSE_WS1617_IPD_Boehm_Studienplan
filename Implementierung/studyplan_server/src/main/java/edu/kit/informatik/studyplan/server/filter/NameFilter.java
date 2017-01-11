package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Modulnamen-Textsuchfilter.
 */
public class NameFilter extends ContainsFilter {
    /**
     * Erzeugt einen neuen Modulnamen-Textsuchfilter mit gegebenem Suchstring.
     * @param substring der Suchstring
     */
    public NameFilter(String substring) {
        super(substring);
    }

    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.NAME;
    }
}
