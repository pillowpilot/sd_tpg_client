package cliente.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;

import java.util.Map;

public class DataModel {
    private Map<String, Scene> sceneMapping;
    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private final ObservableList<ChatMessage> chatMessages = FXCollections.observableArrayList();

    public DataModel(Map<String, Scene> sceneMapping) {
        this.sceneMapping = sceneMapping;
    }

    public Map<String, Scene> getSceneMapping() { return sceneMapping; }

    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    public ObservableList<ChatMessage> getChatMessages() { return chatMessages; }

    public void addChatMessage(ChatMessage chatMessage) { this.chatMessages.add(chatMessage); }
}
