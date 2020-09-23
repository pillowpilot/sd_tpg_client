package messages.responses;

/**
 * Clase abstracta para modelar el campo cuerpo de las respuesta del servidor.
 */
public abstract class Payload {
    /**
     * Método intermediario para el despache según el tipo de dato de la respuesta del servidor.
     * @param visitor Objeto visitante que procesará las respuestas del servidor.
     * @param response Objeto respuesta que almacena los datos a utilizar en el visitante.
     */
    public abstract void accept(ResponseVisitor visitor, Response response);
}
