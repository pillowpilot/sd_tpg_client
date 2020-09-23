package messages.requests;

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

    /**
     * Segundo método despachante del patrón visitante, según el tipo de dato del visitante.
     * @param visitor Objeto visitante que procesará las solicitudes del cliente.
     * @param request Objeto respuesta que almacena los datos a utilizar en el visitante.
     */
    @Override
    public void accept(RequestVisitor visitor, Request request) {
        visitor.visitAvailableContacts(request, this);
    }

    @Override
    public String toString() {
        return "AvailableContactsPayload{" +
                "username='" + username + '\'' +
                '}';
    }
}
