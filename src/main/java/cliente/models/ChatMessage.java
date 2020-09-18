package cliente.models;

import org.jetbrains.annotations.NotNull;

public class ChatMessage {
    private final @NotNull Contact contact;
    private final @NotNull String messageText;

    public ChatMessage(@NotNull Contact contact, @NotNull String messageText) {
        this.contact = contact;
        this.messageText = messageText;
    }

    public @NotNull Contact getContact() {
        return contact;
    }

    public @NotNull String getMessageText() {
        return messageText;
    }

    /**
     * Verifica igualdad.
     * @see <a href="https://www.baeldung.com/java-equals-hashcode-contracts">Detalles</a>
     * @param o Objeto contra el cual verificar
     * @return Verdadero si {@code o} es un ChatMessage y posee el mismo contacto y mensaje, falso sino.
     */
    @Override
    public boolean equals(Object o) {
        if( o == this )
            return true;

        if( !( o instanceof Contact ) )
            return false;

        ChatMessage other = (ChatMessage) o;
        return this.contact.equals(other.contact) && this.messageText.equals(other.messageText);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "contact=" + contact +
                ", messageText='" + messageText + '\'' +
                '}';
    }
}
