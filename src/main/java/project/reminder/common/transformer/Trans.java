package project.reminder.common.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.modelmapper.ModelMapper;

public class Trans {

	private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final ModelMapper modelMapper = new ModelMapper();

    // TODO CALENDAR 타입으로 변경 필요, 클릭시 상세정보로 이동되도록
    public static String createTemplate(String text) {
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
	
    public static String token(String rtn, JsonParser parser) {
        JsonElement element = parser.parse(rtn);       
        return element.getAsJsonObject().get("access_token").getAsString();
    }

	public static <T> T parseJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

	public static String valueAsJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <D, E> E dtoToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public static <D, E> D entityToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
}
