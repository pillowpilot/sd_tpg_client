package cliente;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase responsable de orquestar la aplicación cliente.
 */
public class Client extends Application {
    private static final String uiFilename = "/client_ui.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Punto de inicio de la aplicación.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        UILoader uiLoader = new UILoader(uiFilename);
        uiLoader.load(primaryStage);
    }
}
