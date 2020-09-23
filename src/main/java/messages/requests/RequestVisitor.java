package messages.requests;

/**
 * Inteface para visitantes, parte del patrón visitante sobre las solicitudes del cliente.
 * Se extienden los métodos usuales para lidiar con la separación de información en encabezado y cuerpo.
 */
public interface RequestVisitor {
    void visitAvailableContacts(Request request, AvailableContactsPayload payload);
    void visitRegistrationAttempt(Request request, RegistrationAttemptPayload payload);
    void visitUnregister(Request request, UnregisterPayload payload);
}
