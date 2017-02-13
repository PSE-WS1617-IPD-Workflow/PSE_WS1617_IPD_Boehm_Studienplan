package edu.kit.informatik.studyplan.server.rest;



import java.io.IOException;
import java.util.Objects;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;

/**
 * Provider for a customized ObjectMapper.
 */
@Provider
public class MyObjectMapperProvider implements ContextResolver<ObjectMapper> {
    private final ObjectMapper defaultMapper;

    /**
     * Creates a new ObjectMapperProvider and initializes provided ObjectMapper.
     */
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

    /**
     * Customized serializer module class. Provides (de)serialization for ModuleConstraintType and PreferenceType.
     */
    public static class CustomSerializerModule extends SimpleModule {
        /**
         * Creates a new CustomSerializerModule.
         */
        public CustomSerializerModule() {
            super("CustomSerializerModule", Version.unknownVersion());
            this.addSerializer(ModuleConstraintType.class, new ModuleConstraintTypeSerializer());
            this.addSerializer(PreferenceType.class, new PreferenceTypeSerializer());
            this.addDeserializer(PreferenceType.class, new PreferenceTypeDeserializer());
        }

        /**
         * Serializer for ModuleConstraintType.
         */
        public static class ModuleConstraintTypeSerializer extends JsonSerializer<ModuleConstraintType> {
            /**
             * Empty constructor.
             */
			public ModuleConstraintTypeSerializer() { }
		
            @Override
            public void serialize(ModuleConstraintType value,
                                  JsonGenerator jgen,
                                  SerializerProvider serializerProvider) throws IOException {
                jgen.writeString(value.getDescription());
            }
        }

        /**
         * Serializer for PreferenceType.
         */
        public static class PreferenceTypeSerializer extends JsonSerializer<PreferenceType> {
            /**
             * Empty constructor.
             */
			public PreferenceTypeSerializer() { }
			
            @Override
            public void serialize(PreferenceType value,
                                  JsonGenerator jgen,
                                  SerializerProvider serializerProvider) throws IOException {
                jgen.writeString(value != null ? value.toString().toLowerCase() : "");
            }
        }

        /**
         * Deserializer for PreferenceType.
         */
        public static class PreferenceTypeDeserializer extends JsonDeserializer<PreferenceType> {
            /**
             * Empty constructor.
             */
			public PreferenceTypeDeserializer() { }
			
            @Override
            public PreferenceType deserialize(JsonParser jsonParser,
                                              DeserializationContext deserializationContext)
                    throws IOException {
                if (Objects.equals(jsonParser.getValueAsString(), "")) {
                    return null;
                } else {
                    return PreferenceType.valueOf(jsonParser.getValueAsString().toUpperCase());
                }
            }
        }
    }
}
