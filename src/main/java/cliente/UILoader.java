package cliente;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Carga la interfaz gráfica desde el archivo FXML.
 * @see <a href="https://docs.oracle.com/javafx/2/get_started/fxml_tutorial.htm">Detalles</a>
 */
public class UILoader {
    private Parent root;

    public UILoader(String uiFilename) throws IOException {
        URL uiFilepath = getClass().getResource(uiFilename);
        if(uiFilepath == null)
            throw new FileNotFoundException(
                    String.format("%s not found at %s.", uiFilename, getClass().getResource("/")));

        this.root = FXMLLoader.load(uiFilepath);
    }

    public void load(Stage stage){
        Scene scene = new Scene(root);

        stage.setTitle("FXML Welcome"); // TODO Param to FXML
        stage.setScene(scene);
        stage.show();
    }

}
