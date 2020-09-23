package cliente.messages.responses;

import cliente.messageVisitors.ResponseVisitor;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * DTO de los encabezados de las respuestas del servidor (Java Bean para automatizar la serialización).
 * Domain Model Object que modela las respuestas del servidor, útil para aplicar el patrón visitante.
 */
public class Response {
    @JsonProperty("estado")
    private String state;
    @JsonProperty("mensaje")
    private String message;
    @JsonProperty("tipo_operacion")
    private String operationType;

    @JsonProperty("cuerpo")
    @JsonTypeInfo(use= JsonTypeInfo.Id.NAME, property = "tipo_operacion", include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes(value = {
            @JsonSubTypes.Type(value = AvailableContactsPayload.class, name = "1"),
            @JsonSubTypes.Type(value = RegistrationAttemptPayload.class, name = "5"),
    })
    private Payload payload;

    public Response() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    /**
     * Método para despachar según el tipo de dato de la respuesta del servidor.
     * Se utiliza Payload.accept como intermediario para componer la información sobre @{this} y el payload.
     * @param visitor Objeto visitante que procesará las respuestas del servidor.
     */
    public void accept(ResponseVisitor visitor) {
        payload.accept(visitor, this);
    }

    @Override
    public String toString() {
        return "Response{" +
                "state='" + state + '\'' +
                ", message='" + message + '\'' +
                ", operationType='" + operationType + '\'' +
                ", payload=" + payload +
                '}';
    }
}
