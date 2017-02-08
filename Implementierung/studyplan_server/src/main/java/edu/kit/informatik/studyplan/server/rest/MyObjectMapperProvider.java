package edu.kit.informatik.studyplan.server.rest;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Provider
public class MyObjectMapperProvider implements ContextResolver<ObjectMapper> {
    private final ObjectMapper defaultMapper;

    public MyObjectMapperProvider() {
        defaultMapper = new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT,  true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(new CustomSerializerModule());
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return defaultMapper;
    }

    public static class CustomSerializerModule extends SimpleModule {
        public CustomSerializerModule() {
            super ("CustomSerializerModule", Version.unknownVersion());
            this.addSerializer(ModuleConstraintType.class, new ModuleConstraintTypeSerializer());
            this.addSerializer(PreferenceType.class, new PreferenceTypeSerializer());
            this.addDeserializer(PreferenceType.class, new PreferenceTypeDeserializer());
        }

        public class ModuleConstraintTypeSerializer extends JsonSerializer<ModuleConstraintType> {
			public ModuleConstraintTypeSerializer() { }
		
            @Override
            public void serialize(ModuleConstraintType value,
                                  JsonGenerator jgen,
                                  SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jgen.writeString(value.getDescription());
            }
        }

        public class PreferenceTypeSerializer extends JsonSerializer<PreferenceType> {
			public PreferenceTypeSerializer() { }
			
            @Override
            public void serialize(PreferenceType value,
                                  JsonGenerator jgen,
                                  SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                if (value != null)
                    jgen.writeString(value.toString().toLowerCase());
                else
                    jgen.writeString("");
            }
        }

        public class PreferenceTypeDeserializer extends JsonDeserializer<PreferenceType> {
			public PreferenceTypeDeserializer() { }
			
            @Override
            public PreferenceType deserialize(JsonParser jsonParser,
                                              DeserializationContext deserializationContext)
                    throws IOException, JsonProcessingException {
                if (Objects.equals(jsonParser.getValueAsString(), ""))
                    return null;
                else
                    return PreferenceType.valueOf(jsonParser.getValueAsString().toUpperCase());
            }
        }

        public class PassedModulesSerializer extends JsonSerializer<List<ModuleEntry>> {
			public PassedModulesSerializer() { }
			
            @Override
            public void serialize(List<ModuleEntry> moduleEntries,
                                  JsonGenerator jsonGenerator,
                                  SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

            }
        }
    }
}
