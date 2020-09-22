package cliente.messages.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Carga de las respuestas a las operaciones de lista de contactos disponibles.
 * DTO del cuerpo de las solicitudes de la operación anterior (Java Bean para serialización).
 */
public class AvailableContactsPayload extends Payload {
    @JsonProperty("usuario")
    private String username;

    public AvailableContactsPayload() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AvailableContactsPayload{" +
                "username='" + username + '\'' +
                '}';
    }
}
