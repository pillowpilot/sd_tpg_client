package messages.requests;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Deserializador de cadenas en notación JSON a objetos solicitud del cliente.
 */
public class RequestUnmarshaller {
    public Request fromJSON(String json_message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Request request = objectMapper.readValue(json_message, Request.class);
        return request;
    }
}
