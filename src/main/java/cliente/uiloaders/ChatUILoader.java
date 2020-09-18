package cliente.uiloaders;

import cliente.controllers.ChatController;
import cliente.models.DataModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Carga la interfaz gráfica desde el archivo FXML.
 * @see <a href="https://docs.oracle.com/javafx/2/get_started/fxml_tutorial.htm">Detalles</a>
 */
public class ChatUILoader {
    private Parent root;
    private Scene scene;

    public ChatUILoader(String uiFilename, DataModel dataModel) throws IOException {
        @Nullable URL uiFilepath = getClass().getResource(uiFilename);
        if(uiFilepath == null)
            throw new FileNotFoundException(
                    String.format("%s not found at %s.", uiFilename, getClass().getResource("/"))
            );

        ChatController chatController = new ChatController();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(uiFilepath);
        loader.setController(chatController);
        this.root = loader.load();

        // Delay call to Controller.setDataModel to allow JavaFX to initialize public members of Controller
        chatController.setDataModel(dataModel);

        this.scene = new Scene(root);
    }

    public Scene getScene() {
        return scene;
    }
}
