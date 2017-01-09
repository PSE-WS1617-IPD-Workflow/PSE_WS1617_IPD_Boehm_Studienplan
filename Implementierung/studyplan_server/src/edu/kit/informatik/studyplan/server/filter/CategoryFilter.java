package edu.kit.informatik.studyplan.server.filter;

import java.util.List;

/**
 * Repräsentiert einen Kategorie-Wahlfilter mit den Modulkategorien als Wahlmöglichkeiten.
 */
public class CategoryFilter extends ListFilter {
    /**
     * Erzeugt einen neuen Kategorie-Wahlfilter mit gegebener festgelegter Auswahl.
     *
     * @param selection die Nummer des ausgewählten Elements
     */
    public CategoryFilter(int selection) {
        super(selection);
    }

    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.CATEGORY;
    }

    @Override
    public List<String> getItems() {
        return null;
    }
}
