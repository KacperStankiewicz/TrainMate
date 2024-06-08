package pl.edu.pja.trainmate.core.converter;

import static pl.edu.pja.trainmate.core.config.objectmapper.CustomObjectMapperConfig.createObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.util.Collections;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.boot.json.JsonParseException;
import pl.edu.pja.trainmate.core.domain.exercise.SetParams;

@Converter
public class SetParamsConverter implements AttributeConverter<List<SetParams>, String> {

    private static final String DEFAULT_JSON_VALUE = "[]";

    @Override
    public String convertToDatabaseColumn(List<SetParams> sets) {
        if (sets == null || sets.isEmpty()) {
            return DEFAULT_JSON_VALUE;
        }

        ObjectMapper objectMapper = createObjectMapper();

        return Try.of(() -> objectMapper.writeValueAsString(sets))
            .getOrElseThrow(ex -> new JsonParseException(ex));
    }

    @Override
    public List<SetParams> convertToEntityAttribute(String string) {
        if (string == null || string.trim().length() < 2) {
            return Collections.emptyList();
        }
        return Try.of(() -> fromJson(new TypeReference<List<SetParams>>() {
            }, string))
            .getOrElseThrow(ex -> new JsonParseException(ex));
    }

    private static <T> T fromJson(final TypeReference<T> type, final String jsonString) throws JsonProcessingException {
        return createObjectMapper().readValue(jsonString, type);
    }
}