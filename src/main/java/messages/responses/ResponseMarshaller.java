package messages.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Serializador de objetos respuesta del servidor a cadenas en notación JSON.
 */
public class ResponseMarshaller {
    public String toJSON(Response response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json_message = objectMapper.writeValueAsString(response);
        return json_message;
    }
}
