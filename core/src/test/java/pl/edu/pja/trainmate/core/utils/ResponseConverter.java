package pl.edu.pja.trainmate.core.utils;

import static com.fasterxml.jackson.databind.DeserializationFeature.USE_LONG_FOR_INTS;
import static pl.edu.pja.trainmate.core.config.objectmapper.CustomObjectMapperConfig.createObjectMapper;
import static pl.edu.pja.trainmate.core.config.objectmapper.CustomObjectMapperConfig.createPageObjectMapper;

import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockHttpServletResponse;
import pl.edu.pja.trainmate.core.common.exception.CommonException;

public class ResponseConverter {

    public static <T> Page<T> castResponseToPage(MockHttpServletResponse response, Class<T> clazz) {
        if (response.getErrorMessage() == null) {
            try {
                var objectMapper = createPageObjectMapper(clazz);
                String content = response.getContentAsString();
                return objectMapper.readValue(content, objectMapper.getTypeFactory().constructParametricType(Page.class, clazz));
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert response to Page<" + clazz.getSimpleName() + ">", e);
            }
        } else {
            throw new RuntimeException("Error in response: " + response.getErrorMessage());
        }
    }

    public static <T> T castResponseTo(MockHttpServletResponse response, Class<T> clazz) {
        if (response.getErrorMessage() == null) {
            try {
                var objectMapper = createObjectMapper();
                objectMapper.configure(USE_LONG_FOR_INTS, true);
                return objectMapper.readValue(response.getContentAsString(), clazz);
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert response to " + clazz.getName(), e);
            }
        }
        throw new CommonException(response.getErrorMessage());
    }

    public static <T> List<T> castResponseToList(MockHttpServletResponse response, Class<T> clazz) {
        if (response.getErrorMessage() == null) {
            try {
                var objectMapper = createObjectMapper();
                String content = response.getContentAsString();
                return objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert response to List<" + clazz.getSimpleName() + ">", e);
            }
        } else {
            throw new RuntimeException("Error in response: " + response.getErrorMessage());
        }
    }
}
