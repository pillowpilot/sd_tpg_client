package cliente;

import cliente.models.DataModel;
import cliente.uiloaders.ChatUILoader;
import cliente.uiloaders.LoginUILoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase responsable de orquestar la aplicación cliente.
 */
public class Client extends Application {
    private static final String loginUIFilename = "/login_ui.fxml";
    private static final String chatUIFilename = "/client_ui.fxml";
    private DataModel dataModel = null;
    private Map<String, Scene> sceneMapping = new HashMap<>(); // TODO Change key type

    public static void main(String[] args){
        launch(args);
    }

    /**
     * Punto de inicio de la aplicación.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.dataModel = new DataModel(this.sceneMapping);

        LoginUILoader loginUILoader = new LoginUILoader(loginUIFilename, dataModel);
        sceneMapping.put("login", loginUILoader.getScene());
        ChatUILoader chatUILoader = new ChatUILoader(chatUIFilename, dataModel);
        sceneMapping.put("chat", chatUILoader.getScene());

        primaryStage.setTitle("Login"); // TODO Param to FXML
        primaryStage.setScene(loginUILoader.getScene());
        primaryStage.show();
    }
}
