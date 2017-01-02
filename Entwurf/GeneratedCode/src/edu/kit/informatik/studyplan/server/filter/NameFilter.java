package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Modulnamen-Textsfilter.
 */
public class NameFilter extends ContainsFilter {
    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.NAME;
    }

    /**
     * Erzeugt einen neuen NameFilter mit dem Substring, nach welchem gefiltert werden soll.
     * @param substring der Substring für den Filter
     */
    public NameFilter(String substring) {
        this.substring = substring;
    }
}
