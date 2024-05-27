package pl.edu.pja.trainmate.core.utils;

import static com.fasterxml.jackson.databind.DeserializationFeature.USE_LONG_FOR_INTS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.data.domain.Page;

public class CustomObjectMapperConfig {

    public static ObjectMapper createObjectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.configure(USE_LONG_FOR_INTS, true);
        return objectMapper;
    }

    public static <T> ObjectMapper createPageObjectMapper(Class<T> clazz) {
        var objectMapper = createObjectMapper();
        var module = new SimpleModule();
        module.addDeserializer(Page.class, new PageDeserializer<>(clazz));

        objectMapper.registerModule(module);
        return objectMapper;
    }
}