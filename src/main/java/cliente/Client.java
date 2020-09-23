package cliente;

import cliente.models.DataModel;
import cliente.services.AvailableContactsService;
import cliente.uiloaders.ChatUILoader;
import cliente.uiloaders.LoginUILoader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import messages.requests.*;
import messages.responses.Response;
import messages.responses.ResponseUnmarshaller;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

/**
 * Clase responsable de orquestar la aplicación cliente.
 */
public class Client extends Application {
    // TODO Parameterize this!
    private static final String serverIp = "localhost";
    private static final Integer serverPort = 9876;

    // FXML filenames
    private static final String loginUIFilename = "/login_ui.fxml";
    private static final String chatUIFilename = "/client_ui.fxml";

    // These ids must correspond to the ones in loginUIFilename (see fx:id)
    private static final String loginBtnId = "loginBtn";
    private static final String usernameTextFieldId = "usernameTextField";
    private static final String helpTextId = "helpText";

    private ExecutorService executorService;
    private static Scene loginScene;
    private static Scene chatScene;
    private DataModel dataModel = null;

    public String currentUsername;

    public static void main(String[] args){
        launch(args);
    }

    /**
     * Punto de inicio de la aplicación.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.executorService = Executors.newFixedThreadPool(4);
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

            Response response = null;
            try {
                // get a datagram socket
                DatagramSocket socket = new DatagramSocket();

                RegistrationAttemptPayload payload = new RegistrationAttemptPayload();
                payload.setUsername(usernameCandidate);
                Request request = new Request();
                request.setState(0);
                request.setMessage("ok");
                request.setOperationType("5");
                request.setPayload(payload);

                RequestMarshaller marshaller = new RequestMarshaller();
                String json_message = marshaller.toJSON(request);

                // send request
                byte[] buffer = json_message.getBytes();
                InetAddress address = InetAddress.getByName("localhost");
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9999);
                socket.send(packet);

                // get response
                buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                // display response
                String received_json_message = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received_json_message);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode json_root = mapper.readTree(received_json_message);
                JsonNode json_estado = json_root.get("estado");

                response = new Response();
                response.setState(json_estado.asText());

                System.out.println(response);

                socket.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            System.out.println(">>>" + response);
            if(response.getState().equals("0")) {
                dataModel.setUserLogged(true);
                currentUsername = usernameCandidate;

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
    public void stop() {

        try {
            // get a datagram socket
            DatagramSocket socket = new DatagramSocket();

            UnregisterPayload payload = new UnregisterPayload();
            payload.setUsername(currentUsername);
            Request request = new Request();
            request.setState(0);
            request.setMessage("ok");
            request.setOperationType("6");
            request.setPayload(payload);

            RequestMarshaller marshaller = new RequestMarshaller();
            String json_message = marshaller.toJSON(request);

            // send request
            byte[] buffer = json_message.getBytes();
            InetAddress address = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9999);
            socket.send(packet);

            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private void initializeAllIndependentThreads() {
        BlockingQueue<String> incomingMessages = new LinkedBlockingQueue<>();
        BlockingQueue<String> sendingMessages = new LinkedBlockingQueue<>();


    }
}
