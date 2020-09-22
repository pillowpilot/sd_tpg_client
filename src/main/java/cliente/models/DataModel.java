package cliente.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {
    private BooleanProperty userLogged = new SimpleBooleanProperty(false);
    private final ListProperty<Contact> contacts = new SimpleListProperty<>();
    private final ObservableList<ChatMessage> chatMessages = FXCollections.observableArrayList();

    public boolean isUserLogged() {
        return userLogged.get();
    }

    public BooleanProperty userLoggedProperty() {
        return userLogged;
    }

    public void setUserLogged(boolean userLogged) {
        this.userLogged.set(userLogged);
    }

    public ObservableList<Contact> getContacts() {
        return contacts.get();
    }

    public ListProperty<Contact> contactsProperty() {
        return contacts;
    }

    public void setContacts(ObservableList<Contact> contacts) {
        this.contacts.set(contacts);
    }

    public ObservableList<ChatMessage> getChatMessages() { return chatMessages; }

    public void addChatMessage(ChatMessage chatMessage) { this.chatMessages.add(chatMessage); }
    
}
