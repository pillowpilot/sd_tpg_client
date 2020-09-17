package cliente.models;

import org.jetbrains.annotations.NotNull;

public class ChatMessage {
    private final Contact contact;
    private final String messageText;

    public ChatMessage(@NotNull Contact contact, @NotNull String messageText) {
        this.contact = contact;
        this.messageText = messageText;
    }

    public Contact getContact() {
        return contact;
    }

    public String getMessageText() {
        return messageText;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "contact=" + contact +
                ", messageText='" + messageText + '\'' +
                '}';
    }
}
