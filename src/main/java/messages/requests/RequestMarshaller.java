package messages.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Serializador de objetos solicitud del cliente a cadenas en notación JSON.
 */
public class RequestMarshaller {
    public String toJSON(Request request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json_message = objectMapper.writeValueAsString(request);
        return json_message;
    }
}
