package cliente.models;

import java.io.*;
import java.net.Socket;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {
    private BooleanProperty userLogged = new SimpleBooleanProperty(false);
    private BooleanProperty callOngoing = new SimpleBooleanProperty(false);
    private final ListProperty<Contact> contacts = new SimpleListProperty<>();
    private final ObservableList<ChatMessage> chatMessages = FXCollections.observableArrayList();
    private PrintWriter ENVIO;
    private BufferedReader RECIBO;
    private Socket socket;
    public boolean isUserLogged() {
        return userLogged.get();
    }
    
    public BooleanProperty userLoggedProperty() {
        return userLogged;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public void setUserLogged(boolean userLogged) {
        this.userLogged.set(userLogged);
    }
     public boolean isCallOngoing() {
        return callOngoing.get();
    }
    
    public BooleanProperty callOngoingProperty() {
        return callOngoing;
    }

    public void setCallOngoing(boolean call) {
        this.userLogged.set(call);
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

    public PrintWriter getENVIO() {
        return ENVIO;
    }

    public BufferedReader getRECIBO() {
        return RECIBO;
    }

    public void setENVIO(PrintWriter ENVIO) {
        this.ENVIO = ENVIO;
    }

    public void setRECIBO(BufferedReader RECIBO) {
        this.RECIBO = RECIBO;
    }
    
    public ObservableList<ChatMessage> getChatMessages() { return chatMessages; }

    public void addChatMessage(ChatMessage chatMessage) { this.chatMessages.add(chatMessage); }
    
}
