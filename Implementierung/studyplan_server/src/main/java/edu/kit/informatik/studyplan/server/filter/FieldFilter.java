package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

/**
 * Represents a Field list filter.
 */
public class FieldFilter extends ListFilter<Field> {
    /**
     * Creates a new FieldFilter with a given selected object.
     *
     * @param selection the selected field. Must not be null.
     */
    protected FieldFilter(Field selection) {
        super(selection);
    }

    @Override
    protected String getAttributeName() {
        return ModuleAttributeNames.FIELD;
    }
}
