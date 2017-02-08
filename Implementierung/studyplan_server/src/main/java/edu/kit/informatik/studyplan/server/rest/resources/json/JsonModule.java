package edu.kit.informatik.studyplan.server.rest.resources.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;
import edu.kit.informatik.studyplan.server.rest.MyObjectMapperProvider;
import edu.kit.informatik.studyplan.server.rest.resources.ModuleResource;

import javax.validation.constraints.NotNull;
import java.util.List;

public class JsonModule {
    @NotNull
    private String id;
    private String name;
    private List<Category> categories;
    private Integer semester;
    private CycleType cycleType;
    private Double creditPoints;
    private String lecturer;
//		@JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonSerialize(using = MyObjectMapperProvider.CustomSerializerModule.PreferenceTypeSerializer.class)
    @JsonDeserialize(using = MyObjectMapperProvider.CustomSerializerModule.PreferenceTypeDeserializer.class)
    private PreferenceType preference;
    private Boolean compulsory;
    private String description;
    private List<ModuleConstraint> constraints;

    public JsonModule() {} //for Jackson

    public JsonModule(String id,
                      String name,
                      List<Category> categories,
                      Integer semester,
                      CycleType cycleType,
                      Double creditPoints,
                      String lecturer,
                      PreferenceType preference,
                      Boolean compulsory,
                      String description,
                      List<ModuleConstraint> constraints) {
        this.setId(id);
        this.setName(name);
        this.setCategories(categories);
        this.setSemester(semester);
        this.setCycleType(cycleType);
        this.setCreditPoints(creditPoints);
        this.setLecturer(lecturer);
        this.setPreference(preference);
        this.setCompulsory(compulsory);
        this.setDescription(description);
        this.setConstraints(constraints);
    }

    public static JsonModule fromModule(Module module, Integer semester, PreferenceType preference) {
        return new JsonModule(module.getIdentifier(), module.getName(), module.getCategories(), semester,
                module.getCycleType(), module.getCreditPoints(), module.getModuleDescription().getLecturer(),
                preference, module.isCompulsory(), module.getModuleDescription().getDescriptionText(),
                module.getConstraints());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public CycleType getCycleType() {
        return cycleType;
    }

    public void setCycleType(CycleType cycleType) {
        this.cycleType = cycleType;
    }

    public Double getCreditPoints() {
        return creditPoints;
    }

    public void setCreditPoints(Double creditPoints) {
        this.creditPoints = creditPoints;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public PreferenceType getPreference() {
        return preference;
    }

    public void setPreference(PreferenceType preference) {
        this.preference = preference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ModuleConstraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<ModuleConstraint> constraints) {
        this.constraints = constraints;
    }

    public Boolean isCompulsory() {
        return compulsory;
    }

    public void setCompulsory(Boolean compulsory) {
        this.compulsory = compulsory;
    }
}