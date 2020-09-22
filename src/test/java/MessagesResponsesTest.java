import cliente.messages.responses.*;
import cliente.models.Contact;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessagesResponsesTest {
    @Test
    public void registrationAttemptMarshalling() throws JsonProcessingException {
        ResponseMarshaller marshaller = new ResponseMarshaller();

        RegistrationAttemptPayload payload = new RegistrationAttemptPayload();
        final String username = "a random username";
        payload.setUsername(username);
        Response response = new Response();
        response.setOperationType("5");
        response.setPayload(payload);

        final String json_message = marshaller.toJSON(response);
        assertTrue(json_message.contains("tipo_operacion"));
        assertTrue(json_message.contains("5"));
        assertTrue(json_message.contains("usuario"));
        assertTrue(json_message.contains(username));

        System.out.println(response + " -> " + json_message);
    }

    @Test
    public void registrationAttemptUnmarshalling() throws IOException {
        final String json_message = "{\"tipo_operacion\":\"5\",\"cuerpo\":{\"usuario\":\"a random username\"}}";
        ResponseUnmarshaller unmarshaller = new ResponseUnmarshaller();
        Response response = unmarshaller.fromJSON(json_message);

        assertEquals(response.getOperationType(), "5");
        // Notar que Jackson deserializa a la subclase correcta
        assertEquals(response.getPayload().getClass(), RegistrationAttemptPayload.class);
        RegistrationAttemptPayload payload = (RegistrationAttemptPayload) response.getPayload();
        assertEquals(payload.getUsername(), "a random username");

        System.out.println(json_message + " -> " + response);
    }

    @Test
    public void availableContactsMarshalling() throws JsonProcessingException {
        ResponseMarshaller marshaller = new ResponseMarshaller();

        AvailableContactsPayload payload = new AvailableContactsPayload();
        List<Contact> availableContacts = new ArrayList<>();
        availableContacts.add(new Contact("usernameA"));
        availableContacts.add(new Contact("usernameB"));
        payload.setAvailableContacts(availableContacts);
        Response response = new Response();
        response.setOperationType("1");
        response.setPayload(payload);

        final String json_message = marshaller.toJSON(response);
        assertTrue(json_message.contains("tipo_operacion"));
        assertTrue(json_message.contains("1"));
        assertTrue(json_message.contains("usuarios_disponibles"));
        assertTrue(json_message.contains("usernameA"));
        assertTrue(json_message.contains("usernameB"));

        System.out.println(response + " -> " + json_message);
    }

    @Test
    public void availableContactsUnmarshalling_whenAtleastOneAvailableContact() throws IOException {
        final String json_message = "{\"tipo_operacion\":\"1\",\"cuerpo\":{\"usuarios_disponibles\":[{\"usuario\":\"usernameA\"},{\"usuario\":\"usernameB\"}]}}";
        ResponseUnmarshaller unmarshaller = new ResponseUnmarshaller();
        Response response = unmarshaller.fromJSON(json_message);

        assertEquals(response.getOperationType(), "1");
        // Notar que Jackson deserializa a la subclase correcta
        assertEquals(response.getPayload().getClass(), AvailableContactsPayload.class);
        AvailableContactsPayload payload = (AvailableContactsPayload) response.getPayload();
        assertTrue(payload.getAvailableContacts().contains(new Contact("usernameA")));
        assertTrue(payload.getAvailableContacts().contains(new Contact("usernameB")));

        System.out.println(json_message + " -> " + response);
    }

    @Test
    public void availableContactsUnmarshalling_whenNoAvailableContact() throws IOException {
        final String json_message = "{\"tipo_operacion\":\"1\",\"cuerpo\":{\"usuarios_disponibles\":[]}}";
        ResponseUnmarshaller unmarshaller = new ResponseUnmarshaller();
        Response response = unmarshaller.fromJSON(json_message);

        assertEquals(response.getOperationType(), "1");
        // Notar que Jackson deserializa a la subclase correcta
        assertEquals(response.getPayload().getClass(), AvailableContactsPayload.class);
        AvailableContactsPayload payload = (AvailableContactsPayload) response.getPayload();
        assertTrue(payload.getAvailableContacts().isEmpty());

        System.out.println(json_message + " -> " + response);
    }
}
