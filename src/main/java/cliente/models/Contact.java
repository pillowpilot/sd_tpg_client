package cliente.models;

/**
 * Objecto de dominio que representa a un contacto disponible.
 */
public class Contact {
    private String username;

    public Contact(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Representación en cadena del modelo.
     * Utilizado en la lista de contactos disponibles.
     * @return Cadena representativa del contacto.
     */
    @Override
    public String toString() {
        return username;
    }
}
