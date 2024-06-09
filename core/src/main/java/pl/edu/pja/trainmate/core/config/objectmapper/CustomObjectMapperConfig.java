package pl.edu.pja.trainmate.core.config.objectmapper;

import static com.fasterxml.jackson.databind.DeserializationFeature.USE_LONG_FOR_INTS;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static ObjectMapper createObjectMapper() {
        var objectMapper = new ObjectMapper();
        var javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        objectMapper.registerModule(javaTimeModule);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(DateFormat.getDateInstance());
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

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        return createObjectMapper();
    }
}