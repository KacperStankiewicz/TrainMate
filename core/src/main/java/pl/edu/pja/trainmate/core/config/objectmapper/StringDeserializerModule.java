package pl.edu.pja.trainmate.core.config.objectmapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

public class StringDeserializerModule extends SimpleModule {

    StringDeserializerModule() {
        addDeserializer(String.class, new StdScalarDeserializer<>(String.class) {
            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
                String result = jsonParser.getValueAsString();
                if (StringUtils.isEmpty(result)) {
                    return null;
                }
                return result.trim();
            }
        });
    }
}
