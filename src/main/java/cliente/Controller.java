package cliente;

import cliente.models.DataModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * Vincula los elementos de la vista con el modelo de datos,
 * y maneja las ordenes del usuario.
 */
public class Controller {
    private DataModel dataModel;

    public ListView contacts = null;
    public TextArea messageInput = null;
    public Button sendBtn = null;

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
        contacts.setItems(dataModel.getContacts());
    }

    /**
     * Método a ejecutarse cuando se haga click sobre el botón Enviar.
     * Su nombre debe ser igual al contenido de la propiedad onAction (onAction="#sendMessage").
     */
    @FXML
    public void sendMessage() {
        System.out.println(String.format("[Placeholder] Message: %s", messageInput.getParagraphs()));
        System.out.println("[Placeholder] Enviar!");
    }
}
