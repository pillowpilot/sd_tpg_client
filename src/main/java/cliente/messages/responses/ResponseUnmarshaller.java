package cliente.messages.responses;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Deserializador de cadenas en notación JSON a objetos respuesta del servidor.
 */
public class ResponseUnmarshaller {
    public Response fromJSON(String json_message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Response response = objectMapper.readValue(json_message, Response.class);
        return response;
    }
}
