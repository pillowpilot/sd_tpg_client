package cliente.controllers;

import cliente.asyncTasks.KeepAvailableContactsUpdated;
import cliente.models.DataModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class LoginController {
    private DataModel dataModel;

    public TextField usernameTextField = null;
    public Text helpText = null;

    public void setDataModel(@NotNull DataModel dataModel) {
        this.dataModel = dataModel;
    }

    /**
     * Método a ejecutarse cuando se haga click sobre el botón Entrar.
     * Su nombre debe ser igual al contenido de la propiedad onAction (onAction="#checkCredentials").
     */
    @FXML
    public void checkCredentials() {
        String usernameCandidate = usernameTextField.getText();

        String placeholderUniqueUsername = "unique";
        if(usernameCandidate.equals(placeholderUniqueUsername)) {
            String invalidUsernameMessage = "Entrando...";
            helpText.setText(invalidUsernameMessage);
            helpText.setFill(Color.GREEN);

            Stage primaryStage = (Stage) usernameTextField.getScene().getWindow();

            // TODO Fix dependencies direction
            primaryStage.setTitle("Chat");
            primaryStage.setScene(dataModel.getSceneMapping().get("chat"));

            // TODO Separate this into a more appropiate class
            KeepAvailableContactsUpdated contactsUpdater = new KeepAvailableContactsUpdated();
            dataModel.scheduleAtFixedRate(contactsUpdater, 1000L); // TODO Extract 1000L

        }else{
            String invalidUsernameMessage = "El nombre de usuario no es válido.";
            helpText.setText(invalidUsernameMessage);
            helpText.setFill(Color.ORANGE);
        }
    }
}
