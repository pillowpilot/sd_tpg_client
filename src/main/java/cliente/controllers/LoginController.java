package cliente.controllers;

import cliente.models.DataModel;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

public class LoginController {
    private DataModel dataModel;

    public TextField usernameTextField = null;
    public Text helpText = null;

    public void setDataModel(@NotNull DataModel dataModel) {
        this.dataModel = dataModel;
    }
}
