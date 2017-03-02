package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.ModuleType;
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

    private FilterDescriptor creditPoints = new RangeFilterDescriptor(0, "ects", "ECTS",
            "Das Intervall, in welchem die ECTS der gefundenen Module liegen sollen",
            0, 30, CreditPointsFilter::new);

    private ListFilterDescriptor<Category> category;

    private ListFilterDescriptor<CycleType> cycleType = new ListFilterDescriptor<>(2, "cycletype", "Turnus",
            "Ob die Module im WS, SS oder beidem stattfinden",
            () -> Arrays.asList(CycleType.WINTER_TERM, CycleType.SUMMER_TERM),
            items -> Arrays.asList("WS", "SS"),
            CycleTypeFilter::new);

    private ListFilterDescriptor<ModuleType> moduleType = new ListFilterDescriptor<>(3, "type", "Art",
            "Die Veranstaltungsart der gefundenen Module",
            () -> ModuleDaoFactory.getModuleDao().getModuleTypes(),
            items -> items.stream().map(ModuleType::getName).collect(Collectors.toList()),
            ModuleTypeFilter::new);

    private ListFilterDescriptor<Boolean> compulsory = new ListFilterDescriptor<>(4, "compulsory", "Pflicht/Wahl",
            "Ob nach Pflicht-, Wahlmodulen oder beidem gesucht werden soll",
            () -> Arrays.asList(true, false),
            items -> Arrays.asList("Pflichtmodule", "Wahlmodule"),
            CompulsoryFilter::new);

    private ListFilterDescriptor<Field> field = new ListFilterDescriptor<>(6, "field", "Bereich",
            "Der Bereich der gefundenen Module",
            () -> ModuleDaoFactory.getModuleDao().getFields(discipline),
            items -> items.stream().map(Field::getName).collect(Collectors.toList()),
            FieldFilter::new);

    private ContainsFilterDescriptor name = new ContainsFilterDescriptor(5, "name", "Modulname",
            "Sucht nach Vorkommen des eingegebenen Textes in den Modulnamen",
            NameFilter::new);

    /**
     *
     * @return a list of all published filter descriptors.
     */
    public List<FilterDescriptor> values() {
        return ImmutableList.copyOf(Arrays.asList(
            creditPoints,
            category,
            cycleType,
            moduleType,
            compulsory,
            field,
            name
        ));
    }

    /**
     * Creates a new FilterDescriptorProvider with a given discipline.
     * @param discipline the discipline
     */
    public FilterDescriptorProvider(Discipline discipline) {
        this.discipline = discipline;
        category = new ListFilterDescriptor<>(1, "category", "Kategorie",
                "Die Kategorie der gefundenen Module",
                () -> ModuleDaoFactory.getModuleDao().getCategories(discipline),
                items -> items.stream().map(Category::getName).collect(Collectors.toList()),
                CategoryFilter::new);
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
}
