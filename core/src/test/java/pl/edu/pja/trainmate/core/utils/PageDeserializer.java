package pl.edu.pja.trainmate.core.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;

public class PageDeserializer<T> extends JsonDeserializer<Page<T>> {

    private final Class<T> contentClass;

    public PageDeserializer(Class<T> contentClass) {
        this.contentClass = contentClass;
    }

    @Override
    public Page<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);

        JsonNode contentNode = node.get("content");
        List<T> content = mapper.readValue(contentNode.traverse(mapper), mapper.getTypeFactory().constructCollectionType(List.class, contentClass));

        int page = node.get("number").asInt();
        int size = node.get("size").asInt();
        long totalElements = node.get("totalElements").asLong();

        return new PageImpl<>(content, PageRequest.of(page, size), totalElements);
    }
}