package messages.requests;

/**
 * Clase abstracta para modelar el campo cuerpo de las solicitudes del cliente.
 */
public abstract class Payload {
    /**
     * Método intermediario para el despache según el tipo de dato de la respuesta del servidor.
     * @param visitor Objeto visitante que procesará las respuestas del servidor.
     * @param request Objeto respuesta que almacena los datos a utilizar en el visitante.
     */
    public abstract void accept(RequestVisitor visitor, Request request);
}
