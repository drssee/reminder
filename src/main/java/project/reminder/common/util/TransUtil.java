package project.reminder.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletInputStream;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class TransUtil {

	private final ObjectMapper objectMapper;

    private final ModelMapper modelMapper;

    public String createTemplate(String text) {
        return String.format("template_object={\n"
                        + "        \"object_type\": \"text\",\n"
                        + "        \"text\": \"%s\",\n"
                        + "        \"link\": {\n"
                        + "                \"web_url\": \"http://localhost:8080/sorry.html\",\n"
                        + "                \"mobile_web_url\": \"http://localhost:8080/sorry.html\"\n"
                        + "            }\n"
                        + "    }",
                text);
    }
	
    public String token(String rtn, JsonParser parser) {
        JsonElement element = parser.parse(rtn);       
        return element.getAsJsonObject().get("access_token").getAsString();
    }

	public <T> T parseJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

	public String valueAsJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <D, E> E dtoToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public <D, E> D entityToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <T> T parseBody(InputStream inputStream, Class<T> bodyType) throws IOException {
        return objectMapper.readValue(inputStream, bodyType);
    }
}
