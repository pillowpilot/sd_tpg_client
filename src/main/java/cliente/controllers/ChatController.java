package cliente.controllers;

import cliente.models.ChatMessage;
import cliente.models.Contact;
import cliente.models.DataModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.jetbrains.annotations.NotNull;

/**
 * Vincula los elementos de la vista con el modelo de datos,
 * y maneja las ordenes del usuario.
 */
public class ChatController {
    private DataModel dataModel;

    public ListView<Contact> contacts = null;
    public TextArea chatTextArea = null;
    public TextArea messageInput = null;
    public Button sendBtn = null;

    public void setDataModel(@NotNull DataModel dataModel) {
        this.dataModel = dataModel;

        contacts.setItems(dataModel.getContacts());

        dataModel.getChatMessages().addListener(new ListChangeListener<ChatMessage>() {
            @Override
            public void onChanged(Change<? extends ChatMessage> c) {
                StringBuilder stringBuilder = new StringBuilder();
                for(ChatMessage chatMessage: c.getList()) {
                    System.out.println(chatMessage);
                    stringBuilder.append(chatMessage);
                    stringBuilder.append("\n");
                }

                chatTextArea.setText(stringBuilder.toString());
            }
        });
    }

    /**
     * Método a ejecutarse cuando se haga click sobre el botón Enviar.
     * Su nombre debe ser igual al contenido de la propiedad onAction (onAction="#sendMessage").
     */
    @FXML
    public void sendMessage() {
        System.out.printf("[Placeholder] Message: %s%n", messageInput.getParagraphs());
        System.out.println("[Placeholder] Enviar!");

        Contact contact = new Contact("a random guy");
        dataModel.getContacts().add(contact);
        dataModel.getChatMessages().add(new ChatMessage(contact, "some message"));

        System.out.printf("[Placeholder] Message: %s%n", messageInput.getParagraphs());
        System.out.println("[Placeholder] Enviar!");

        //String currentChat = chatTextArea.getText();
        //chatTextArea.setText(currentChat + "Some stuff");
    }
}
