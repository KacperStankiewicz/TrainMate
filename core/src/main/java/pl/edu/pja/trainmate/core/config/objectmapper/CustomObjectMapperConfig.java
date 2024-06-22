package pl.edu.pja.trainmate.core.config.objectmapper;

import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;

@Configuration
public class CustomObjectMapperConfig {

    private static ObjectMapper instance;

    private static synchronized ObjectMapper getInstance() {
        if (instance == null) {
            instance = JsonMapper.builder()
                .enable(ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();

            var javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
            instance.registerModule(javaTimeModule);

            instance.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            instance.setDateFormat(DateFormat.getDateInstance());
            instance.configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
            instance.registerModule(new StringDeserializerModule());
        }
        return instance;
    }

    public static ObjectMapper createObjectMapper() {
        return getInstance();
    }

    public static <T> ObjectMapper createPageObjectMapper(Class<T> clazz) {
        var objectMapper = getInstance();
        var module = new SimpleModule();
        module.addDeserializer(Page.class, new PageDeserializer<>(clazz));
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        return getInstance();
    }
}