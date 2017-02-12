package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.ModuleType;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import jersey.repackaged.com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;

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
     *
     * @return the credit points filter descriptor
     */
    public FilterDescriptor CREDIT_POINTS() {
        return new FilterDescriptor(0, new CreditPointsFilter(0, 30, 0, 30), "ECTS",
                "Das Intervall, in welchem die ECTS der gefundenen Module liegen sollen");
    };

    /**
     *
     * @return the category filter descriptor
     */
    public FilterDescriptor CATEGORY() {
        Category defaultCategory = Utils.withModuleDao(moduleDao -> moduleDao.getCategories(discipline)).get(0);
        return new FilterDescriptor(1, new CategoryFilter(defaultCategory, discipline), "Kategorie",
                "Die Kategorie der gefundenen Module");
    };

    /**
     *
     * @return the cycle type filter descriptor
     */
    public FilterDescriptor CYCLE_TYPE() {
        return new FilterDescriptor(2, new CycleTypeFilter(CycleType.BOTH), "Turnus",
                "Ob die Module im WS, SS oder beidem stattfinden");
    };

    /**
     *
     * @return the module type filter descriptor
     */
    public FilterDescriptor MODULE_TYPE() {
        ModuleType defaultModuleType = Utils.withModuleDao(ModuleDao::getModuleTypes).get(0);
        return new FilterDescriptor(3, new ModuleTypeFilter(defaultModuleType), "Art",
                "Die Veranstaltungsart der gefundenen Module");
    };

    /**
     * the compulsory filter descriptor
     * @return
     */
    public FilterDescriptor COMPULSORY() {
        return new FilterDescriptor(4, new CompulsoryFilter(true), "Pflicht/Wahl",
                "Ob nach Pflicht-, Wahlmodulen oder beidem gesucht werden soll");
    };

    /**
     *
     * @return the name filter descriptor
     */
    public FilterDescriptor NAME() {
        return new FilterDescriptor(5, new NameFilter(""), "Modulname",
                "Sucht nach Vorkommen des eingegebenen Textes in den Modulnamen");
    };
}
