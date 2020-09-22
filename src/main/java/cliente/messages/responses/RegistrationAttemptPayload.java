package cliente.messages.responses;

import cliente.messageVisitors.ResponseVisitor;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Carga de las respuestas a las operaciones del intento de registro de un usuario nuevo.
 * DTO del cuerpo de las respuestas de la operación anterior (Java Bean para serialización).
 */
public class RegistrationAttemptPayload extends Payload {
    @JsonProperty("usuario")
    private String username;

    public RegistrationAttemptPayload() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Segundo método despachante del patrón visitante, según el tipo de dato del visitante.
     * @param visitor Objeto visitante que procesará las respuestas del servidor.
     * @param response Objeto respuesta que almacena los datos a utilizar en el visitante.
     */
    @Override
    public void accept(ResponseVisitor visitor, Response response) {
        visitor.visitRegistrationAttempt(response, this);
    }

    @Override
    public String toString() {
        return "RegistrationAttemptPayload{" +
                "username='" + username + '\'' +
                '}';
    }
}
