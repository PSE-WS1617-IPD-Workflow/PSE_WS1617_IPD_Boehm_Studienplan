package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Kategorie-Wahlfilter.
 */
public class CategoryFilter extends ListFilter {
    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.CATEGORY;
    }

    /**
     * Liefert die Modulkategorien als Wahlmöglichkeiten.
     * @return  die Wahlmöglichkeiten des Auswahlfilters
     */
    @Override
    public String[] getItems() {
        return new String[0];
    }
}
