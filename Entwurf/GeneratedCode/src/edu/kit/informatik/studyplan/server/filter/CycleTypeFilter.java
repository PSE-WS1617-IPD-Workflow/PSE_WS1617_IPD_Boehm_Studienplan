package edu.kit.informatik.studyplan.server.filter;

public class CycleTypeFilter extends ListFilter {
    @Override
    public FilterDescriptor getDescriptor() {
        return FilterDescriptor.CYCLE_TYPE;
    }

    /**
     * Liefert Filterung nach Winter-, Sommersemester oder beidem als Wahlmöglichkeiten.
     * @return  die Wahlmöglichkeiten des Auswahlfilters
     */
    @Override
    public String[] getItems() {
        return new String[0];
    }
}
