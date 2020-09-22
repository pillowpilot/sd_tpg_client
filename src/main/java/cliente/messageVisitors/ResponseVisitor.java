package cliente.messageVisitors;

import cliente.messages.responses.AvailableContactsPayload;
import cliente.messages.responses.RegistrationAttemptPayload;
import cliente.messages.responses.Response;

/**
 * Inteface para visitantes, parte del patrón visitante sobre las respuestas del servidor.
 * Se extienden los métodos usuales para lidiar con la separación de información en encabezado y cuerpo.
 */
public interface ResponseVisitor {
    void visitAvailableContacts(Response response, AvailableContactsPayload payload);
    void visitRegistrationAttempt(Response response, RegistrationAttemptPayload payload);
}
