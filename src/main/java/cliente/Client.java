package cliente;

import cliente.asyncTasks.KeepAvailableContactsUpdated;
import cliente.models.DataModel;
import cliente.services.AvailableContactsService;
import cliente.uiloaders.ChatUILoader;
import cliente.uiloaders.LoginUILoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Clase responsable de orquestar la aplicación cliente.
 */
public class Client extends Application {
    // TODO Parameterize this!
    private static final String serverIp = "localhost";
    private static final Integer serverPort = 9876;

    //
    private static final String loginUIFilename = "/login_ui.fxml";
    private static final String chatUIFilename = "/client_ui.fxml";

    // These ids must correspond to the ones in loginUIFilename (see fx:id)
    private static final String loginBtnId = "loginBtn";
    private static final String usernameTextFieldId = "usernameTextField";
    private static final String helpTextId = "helpText";

    private static Scene loginScene;
    private static Scene chatScene;
    private DataModel dataModel = null;

    public static void main(String[] args){
        launch(args);
    }

    /**
     * Punto de inicio de la aplicación.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.dataModel = new DataModel();

        LoginUILoader loginUILoader = new LoginUILoader(loginUIFilename, dataModel);
        loginScene = loginUILoader.getScene();

        ChatUILoader chatUILoader = new ChatUILoader(chatUIFilename, dataModel);
        chatScene = chatUILoader.getScene();

        primaryStage.setTitle("Login"); // TODO Param to FXML
        primaryStage.setScene(loginUILoader.getScene());
        primaryStage.show();

        // Scene.lookup MUST be called after Stage.show
        Button loginBtn = (Button) loginUILoader.getScene().lookup("#" + loginBtnId);
        loginBtn.setOnAction(event -> {
            final Scene loginScene = loginUILoader.getScene();

            final TextField usernameTextField = (TextField) loginScene.lookup("#" + usernameTextFieldId);
            final String usernameCandidate = usernameTextField.getText();

            final Text helpText = (Text) loginScene.lookup("#" + helpTextId);

            String placeholderUniqueUsername = "unique";
            if(usernameCandidate.equals(placeholderUniqueUsername)) {
                dataModel.setUserLogged(true);

                String validUsernameMessage = "Entrando...";
                helpText.setText(validUsernameMessage);
                helpText.setFill(Color.GREEN);

                primaryStage.setTitle("Chat");
                primaryStage.setScene(chatScene);

                AvailableContactsService availableContactsService = new AvailableContactsService();
                availableContactsService.setServerIp(serverIp);
                availableContactsService.setServerPort(serverPort);
                Bindings.bindBidirectional(availableContactsService.userLoggedProperty(), dataModel.userLoggedProperty());
                Bindings.bindBidirectional(availableContactsService.uiContactsProperty(), dataModel.contactsProperty());
                availableContactsService.setPeriod(Duration.seconds(1));
                availableContactsService.start();
            }else{
                String invalidUsernameMessage = "El nombre de usuario no es válido.";
                helpText.setText(invalidUsernameMessage);
                helpText.setFill(Color.ORANGE);
            }
        });
    }

    /**
     * Método llamado antes de la finalización de la aplicación.
     */
    @Override
    public void stop() { }
}
