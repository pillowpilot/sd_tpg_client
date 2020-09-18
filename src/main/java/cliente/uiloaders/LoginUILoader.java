package cliente.uiloaders;

import cliente.controllers.LoginController;
import cliente.models.DataModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class LoginUILoader {
    private Parent root;
    private Scene scene;

    public LoginUILoader(String uiFilename, DataModel dataModel) throws IOException {
        @Nullable URL uiFilepath = getClass().getResource(uiFilename);
        if(uiFilepath == null)
            throw new FileNotFoundException(
                    String.format("%s not found at %s.", uiFilename, getClass().getResource("/"))
            );

        LoginController loginController = new LoginController();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(uiFilepath);
        loader.setController(loginController);
        this.root = loader.load();

        // Delay call to Controller.setDataModel to allow JavaFX to initialize public members of Controller
        loginController.setDataModel(dataModel);

        this.scene = new Scene(root);
    }

    public Scene getScene() {
        return scene;
    }
}
