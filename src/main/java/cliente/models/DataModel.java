package cliente.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {
    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private final ObservableList<ChatMessage> chatMessages = FXCollections.observableArrayList();

    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    public ObservableList<ChatMessage> getChatMessages() { return chatMessages; }

    public void addChatMessage(ChatMessage chatMessage) { this.chatMessages.add(chatMessage); }
}
