package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Objecto de dominio que representa a un contacto disponible.
 * Además es un Java Bean para automatizar su serialización/deserialización.
 */
public class Contact {
    @JsonProperty("usuario")
    private @NotNull String username; // This must be unique

    public Contact() { }

    public Contact(@NotNull String username) {
        this.username = username;
    }

    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    /**
     * Verifica igualdad.
     * @see <a href="https://www.baeldung.com/java-equals-hashcode-contracts">Detalles</a>
     * @param o Objeto contra el cual verificar
     * @return Verdadero si {@code o} es un Contact y posee el mismo nombre de usuario, falso sino.
     */
    @Override
    public boolean equals(Object o) {
        if( o == this )
            return true;

        if( !( o instanceof Contact ) )
            return false;

        Contact other = (Contact) o;
        return this.username.equals(other.username);
    }

    /**
     * Representación en cadena del modelo.
     * Utilizado en la lista de contactos disponibles.
     * @return Cadena representativa del contacto.
     */
    @Override
    public String toString() {
        return "Contact{" +
                "username='" + username + '\'' +
                '}';
    }
}
