import messages.requests.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessagesRequestsTest {
    @Test
    public void registrationAttemptMarshalling() throws JsonProcessingException {
        RequestMarshaller marshaller = new RequestMarshaller();

        RegistrationAttemptPayload payload = new RegistrationAttemptPayload();
        final String username = "a random username";
        payload.setUsername(username);
        Request request = new Request();
        request.setState(0);
        request.setMessage("ok");
        request.setOperationType("5");
        request.setPayload(payload);

        final String json_message = marshaller.toJSON(request);
        assertTrue(json_message.contains("estado"));
        assertTrue(json_message.contains("0"));
        assertTrue(json_message.contains("mensaje"));
        assertTrue(json_message.contains("ok"));
        assertTrue(json_message.contains("tipo_operacion"));
        assertTrue(json_message.contains("5"));
        assertTrue(json_message.contains("usuario"));
        assertTrue(json_message.contains(username));

        System.out.println(request + " -> " + json_message);
    }

    @Test
    public void registrationAttemptUnmarshalling() throws IOException {
        final String json_message = "{\"estado\":0,\"mensaje\":\"ok\",\"tipo_operacion\":\"5\",\"cuerpo\":{\"usuario\":\"a random username\"}}";
        RequestUnmarshaller unmarshaller = new RequestUnmarshaller();
        Request request = unmarshaller.fromJSON(json_message);

        assertEquals(request.getState(), 0);
        assertEquals(request.getMessage(), "ok");
        assertEquals(request.getOperationType(), "5");
        // Notar que Jackson deserializa a la subclase correcta
        assertEquals(request.getPayload().getClass(), RegistrationAttemptPayload.class);
        RegistrationAttemptPayload payload = (RegistrationAttemptPayload) request.getPayload();
        assertEquals(payload.getUsername(), "a random username");

        System.out.println(json_message + " -> " + request);
    }

    @Test
    public void availableContactsMarshalling() throws JsonProcessingException {
        RequestMarshaller marshaller = new RequestMarshaller();

        AvailableContactsPayload payload = new AvailableContactsPayload();
        final String username = "an username";
        payload.setUsername(username);
        Request request = new Request();
        request.setState(0);
        request.setMessage("ok");
        request.setOperationType("1");
        request.setPayload(payload);

        final String json_message = marshaller.toJSON(request);
        assertTrue(json_message.contains("estado"));
        assertTrue(json_message.contains("0"));
        assertTrue(json_message.contains("mensaje"));
        assertTrue(json_message.contains("ok"));
        assertTrue(json_message.contains("tipo_operacion"));
        assertTrue(json_message.contains("1"));
        assertTrue(json_message.contains("usuario"));
        assertTrue(json_message.contains(username));

        System.out.println(request + " -> " + json_message);
    }

    @Test
    public void availableContactsUnmarshalling() throws IOException {
        final String json_message = "{\"estado\":0,\"mensaje\":\"ok\",\"tipo_operacion\":\"1\",\"cuerpo\":{\"usuario\":\"an username\"}}";
        RequestUnmarshaller unmarshaller = new RequestUnmarshaller();
        Request request = unmarshaller.fromJSON(json_message);

        assertEquals(request.getState(), 0);
        assertEquals(request.getMessage(), "ok");
        assertEquals(request.getOperationType(), "1");
        // Notar que Jackson deserializa a la subclase correcta
        assertEquals(request.getPayload().getClass(), AvailableContactsPayload.class);
        AvailableContactsPayload payload = (AvailableContactsPayload) request.getPayload();
        assertEquals(payload.getUsername(), "an username");

        System.out.println(json_message + " -> " + request);
    }
}
