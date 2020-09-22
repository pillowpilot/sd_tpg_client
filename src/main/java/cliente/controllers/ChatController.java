package cliente.controllers;

import cliente.models.ChatMessage;
import cliente.models.Contact;
import cliente.models.DataModel;
import cliente.models.MensajeLlamada;
import java.io.PrintWriter;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

        dataModel.contactsProperty().addListener((ListChangeListener<Contact>) c ->
                Platform.runLater(
                        () -> contacts.setItems((ObservableList<Contact>) c.getList())
                ));

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
        /*
            MensajeLlamada mensajeE = new MensajeLlamada();
            mensajeE.setEstado("0");
            mensajeE.setMensaje("ok");
            mensajeE.setTipo_operacion("3");
            mensajeE.setTexto(messageInput.getParagraphs().toString());
            mensajeE.setEmisor();
            mensajeE.setReceptor()
            SendtoServer(mensajeE);
        
        */
    }
    /**
     * Método a ejecutarse cuando se haga click sobre el botón Terminar Llamada.
     * Su nombre debe ser igual al contenido de la propiedad onAction (onAction="#sendMessage").
     */
    public void TerminarLlamada(){
           dataModel.setCallOngoing(false);
           
           
           Contact contact = new Contact("Sistema");
           
         
           dataModel.getChatMessages().add(new ChatMessage(contact, "Lamada Finalizada"));

           
           MensajeLlamada mensajeE = new MensajeLlamada();
           mensajeE.setEstado("0");
           mensajeE.setMensaje("ok");
           mensajeE.setTipo_operacion("4");
           mensajeE.setTexto("");
           
           /*
           mensajeE.setEmisor();
            mensajeE.setReceptor();
           
           */
           SendtoServer(mensajeE);
    }
    /**
     * Método a ejecutarse cuando se haga click sobre el botón Iniciar Llamada.
     * 
     */
     public void IniciarLlamada(){
           dataModel.setCallOngoing(true);
           
           
           Contact contact = new Contact("Sistema");
           
         
           dataModel.getChatMessages().add(new ChatMessage(contact, "Iniciando Llamada...."));

           
           MensajeLlamada mensajeE = new MensajeLlamada();
           mensajeE.setEstado("-1");
           mensajeE.setMensaje("ok");
           mensajeE.setTipo_operacion("2");
           mensajeE.setTexto("");
          
           /*
           mensajeE.setEmisor();
            mensajeE.setReceptor();
           
           */
           SendtoServer(mensajeE);
    }
     
     public void SendtoServer(MensajeLlamada mensaje){
     
         PrintWriter envio = dataModel.getENVIO();
         envio.println(mensaje);
     }
}
