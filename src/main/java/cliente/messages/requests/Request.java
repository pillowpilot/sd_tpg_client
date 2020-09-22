package cliente.messages.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * DTO de los encabezados de las solicitudes del cliente.
 * Domain Model Object que modela las solicitudes del cliente, útil para aplicar el patrón visitante.
 */
public class Request {
    @JsonProperty("tipo_operacion")
    private String operationType;

    @JsonProperty("cuerpo")
    @JsonTypeInfo(use= JsonTypeInfo.Id.NAME, property = "tipo_operacion", include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes(value = {
            @JsonSubTypes.Type(value = AvailableContactsPayload.class, name = "1"),
            @JsonSubTypes.Type(value = RegistrationAttemptPayload.class, name = "5"),
    })
    private Payload payload;

    public Request() {
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

    @Override
    public String toString() {
        return "Request{" +
                "operationType='" + operationType + '\'' +
                ", payload=" + payload +
                '}';
    }
}
