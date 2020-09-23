package messages.responses;

import models.Contact;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Carga de las respuestas a las operaciones de lista de contactos disponibles.
 * DTO del cuerpo de las respuestas de la operación anterior (Java Bean para serialización).
 */
public class AvailableContactsPayload extends Payload {
    @JsonProperty("usuarios_disponibles")
    private List<Contact> availableContacts;

    public AvailableContactsPayload() {
    }

    public List<Contact> getAvailableContacts() {
        return availableContacts;
    }

    public void setAvailableContacts(List<Contact> availableContacts) {
        this.availableContacts = availableContacts;
    }

    /**
     * Segundo método despachante del patrón visitante, según el tipo de dato del visitante.
     * @param visitor Objeto visitante que procesará las respuestas del servidor.
     * @param response Objeto respuesta que almacena los datos a utilizar en el visitante.
     */
    @Override
    public void accept(ResponseVisitor visitor, Response response) {
        visitor.visitAvailableContacts(response, this);
    }

    @Override
    public String toString() {
        return "AvailableContactsPayload{" +
                "availableContacts=" + availableContacts +
                '}';
    }
}
