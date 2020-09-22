package cliente.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DataModel {
    private Timer timer;
    private Map<String, Scene> sceneMapping;
    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private final ObservableList<ChatMessage> chatMessages = FXCollections.observableArrayList();

    public DataModel(Map<String, Scene> sceneMapping, @NotNull Timer timer) {
        this.sceneMapping = sceneMapping;
        this.timer = timer;
    }

    public void scheduleAtFixedRate(TimerTask task, Long period) {
        Long initialDelay = 0L;
        timer.scheduleAtFixedRate(task, initialDelay, period);
    }

    public Map<String, Scene> getSceneMapping() { return sceneMapping; }

    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    public ObservableList<ChatMessage> getChatMessages() { return chatMessages; }

    public void addChatMessage(ChatMessage chatMessage) { this.chatMessages.add(chatMessage); }
}
