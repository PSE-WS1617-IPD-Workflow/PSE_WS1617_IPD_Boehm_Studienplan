package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.*;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import jersey.repackaged.com.google.common.collect.ImmutableList;

import javax.ws.rs.BadRequestException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides published filter descriptors.
 */
public final class FilterDescriptorProvider {
    private Discipline discipline;

    /**
     *
     * @return a list of all published filter descriptors.
     */
    public List<FilterDescriptor> values() {
        return ImmutableList.copyOf(Arrays.asList(
            CREDIT_POINTS(),
            CATEGORY(),
            CYCLE_TYPE(),
            MODULE_TYPE(),
            COMPULSORY(),
            FIELD(),
            NAME()
        ));
    }

    /**
     * Creates a new FilterDescriptorProvider with a given discipline.
     * @param discipline the discipline
     */
    public FilterDescriptorProvider(Discipline discipline) {
        this.discipline = discipline;
    }

    /**
     * Returns the FilterDescriptor with the given uriIdentifier.
     * @param uriIdentifier GET request URI identifier string
     * @return the FilterDescriptor
     * @throws BadRequestException if FilterDescriptor with uriIdentifier doesn't exist
     */
    public FilterDescriptor getDescriptorFromUriIdentifier(String uriIdentifier) throws BadRequestException {
        return values().stream()
                .filter(descriptor -> descriptor.getFilterUriIdentifier().equals(uriIdentifier))
                .findFirst().orElseThrow(BadRequestException::new);
    }

    /**
     *
     * @return the credit points filter descriptor
     */
    public FilterDescriptor CREDIT_POINTS() {
        return new RangeFilterDescriptor(0, "ects", "ECTS",
                "Das Intervall, in welchem die ECTS der gefundenen Module liegen sollen",
                0, 30, CreditPointsFilter::new);
    };

    /**
     *
     * @return the category filter descriptor
     */
    public FilterDescriptor CATEGORY() {
        return new ListFilterDescriptor<>(1, "category", "Kategorie",
                "Die Kategorie der gefundenen Module",
                () -> ModuleDaoFactory.getModuleDao().getCategories(discipline),
                items -> items.stream().map(Category::getName).collect(Collectors.toList()),
                CategoryFilter::new);
    };

    /**
     *
     * @return the cycle type filter descriptor
     */
    public FilterDescriptor CYCLE_TYPE() {
        return new ListFilterDescriptor<>(2, "cycletype", "Turnus",
                "Ob die Module im WS, SS oder beidem stattfinden",
                () -> Arrays.asList(CycleType.WINTER_TERM, CycleType.SUMMER_TERM),
                items -> Arrays.asList("WS", "SS"),
                CycleTypeFilter::new);
    };

    /**
     *
     * @return the module type filter descriptor
     */
    public FilterDescriptor MODULE_TYPE() {
        return new ListFilterDescriptor<>(3, "type", "Art",
                "Die Veranstaltungsart der gefundenen Module",
                () -> ModuleDaoFactory.getModuleDao().getModuleTypes(),
                items -> items.stream().map(ModuleType::getName).collect(Collectors.toList()),
                ModuleTypeFilter::new);
    };

    /**
     *
     * @return the compulsory filter descriptor
     */
    public FilterDescriptor COMPULSORY() {
        return new ListFilterDescriptor<>(4, "compulsory", "Pflicht/Wahl",
                "Ob nach Pflicht-, Wahlmodulen oder beidem gesucht werden soll",
                () -> Arrays.asList(true, false),
                items -> Arrays.asList("Pflichtmodule", "Wahlmodule"),
                CompulsoryFilter::new);
    };

    public FilterDescriptor FIELD() {
        return new ListFilterDescriptor<>(6, "field", "Bereich",
                "Der Bereich der gefundenen Module",
                () -> ModuleDaoFactory.getModuleDao().getFields(discipline),
                items -> items.stream().map(Field::getName).collect(Collectors.toList()),
                FieldFilter::new);
    }

    /**
     *
     * @return the name filter descriptor
     */
    public FilterDescriptor NAME() {
        return new ContainsFilterDescriptor(5, "name", "Modulname",
                "Sucht nach Vorkommen des eingegebenen Textes in den Modulnamen",
                NameFilter::new);
    };
}
