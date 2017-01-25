package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import jersey.repackaged.com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;

public final class FilterDescriptorProvider {
    private Discipline discipline;
    public List<FilterDescriptor> values() {
        return ImmutableList.copyOf(Arrays.asList(
            CREDIT_POINTS(),
            CATEGORY(),
            CYCLE_TYPE(),
            MODULE_TYPE(),
            COMPULSORY(),
            NAME()
        ));
    }

    public FilterDescriptorProvider(Discipline discipline) {
        this.discipline = discipline;
    }

    public FilterDescriptor CREDIT_POINTS() {
        return new FilterDescriptor(0, new CreditPointsFilter(0,30,0,30), "ECTS",
                "Das Intervall, in welchem die ECTS der gefundenen Module liegen sollen");
    };

    public FilterDescriptor CATEGORY() {
        return new FilterDescriptor(1, new CategoryFilter(0, discipline),"Kategorie",
                "Die Kategorie der gefundenen Module");
    };

    public FilterDescriptor CYCLE_TYPE() {
        return new FilterDescriptor(2, new CycleTypeFilter(0), "Turnus",
                "Ob die Module im WS, SS oder beidem stattfinden");
    };

    public FilterDescriptor MODULE_TYPE() {
        return new FilterDescriptor(3, new ModuleTypeFilter(0), "Art",
                "Die Veranstaltungsart der gefundenen Module");
    };

    public FilterDescriptor COMPULSORY() {
        return new FilterDescriptor(4, new CompulsoryFilter(0), "Pflicht/Wahl",
                "Ob nach Pflicht-, Wahlmodulen oder beidem gesucht werden soll ");
    };

    public FilterDescriptor NAME() {
        return new FilterDescriptor(5, new NameFilter(""), "Modulname",
                "Sucht nach Vorkommen des eingegebenen Textes in den Modulnamen");
    };
}
