package cliente.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import messages.requests.AvailableContactsPayload;
import messages.requests.Request;
import messages.requests.RequestMarshaller;
import messages.responses.Response;
import messages.responses.ResponseUnmarshaller;
import models.Contact;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @see <a href="https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm">Detalles</a>
 */
public class AvailableContactsService extends ScheduledService<List<Contact>> {
    // When implementing the subclasses of the Service class,
    // be sure to expose the input parameters to the Task object as properties of the subclass.
    private StringProperty serverIp = new SimpleStringProperty();
    private IntegerProperty serverPort = new SimpleIntegerProperty();
    private BooleanProperty userLogged = new SimpleBooleanProperty();
    private ListProperty<Contact> uiContacts = new SimpleListProperty<>();

    /**
     * @see <a href="https://rterp.wordpress.com/2015/09/21/binding-a-list-of-strings-to-a-javafx-listview/">Detalles</a>
     * @return
     */
    @Override
    protected Task<List<Contact>> createTask() {
        final String serverIp = getServerIp();
        final Integer serverPort = getServerPort();
        final Boolean isUserLogged = isUserLogged();

        return new Task<List<Contact>>() {
            @Override
            protected List<Contact> call() {
                if(isUserLogged.booleanValue()) {

                    List<Contact> contacts = new ArrayList<>();

                    try {
                        // get a datagram socket
                        DatagramSocket socket = new DatagramSocket();

                        AvailableContactsPayload payload = new AvailableContactsPayload();
                        payload.setUsername("");
                        Request request = new Request();
                        request.setState(0);
                        request.setMessage("ok");
                        request.setOperationType("1");
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
                        System.out.println("availablecontactsservice " + received_json_message);

                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode json_root = mapper.readTree(received_json_message);
                        JsonNode json_cuerpo = json_root.get("cuerpo");
                        JsonNode json_lista_disponibles = json_cuerpo.get("usuarios_disponibles");
                        System.out.println(json_lista_disponibles);
                        for (Iterator<JsonNode> it = json_lista_disponibles.elements(); it.hasNext(); ) {
                            JsonNode node = it.next();
                            String username = ((JsonNode) node.get("usuario")).asText();
                            Contact contact = new Contact();
                            contact.setUsername(username);
                            contacts.add(contact);
                        }

                        socket.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }



                    // uiContacts.addAll is not supported!
                    // uiContacts modifications must run through Platform.runLater
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            uiContacts.clear();
                            uiContacts.set(FXCollections.observableArrayList(contacts));
                        }
                    });

                    return contacts;
                }else{
                    return new ArrayList<>();
                }
            }
        };
    }

    public String getServerIp() {
        return serverIp.get();
    }

    public StringProperty serverIpProperty() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp.set(serverIp);
    }

    public int getServerPort() {
        return serverPort.get();
    }

    public IntegerProperty serverPortProperty() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort.set(serverPort);
    }

    public boolean isUserLogged() {
        return userLogged.get();
    }

    public BooleanProperty userLoggedProperty() {
        return userLogged;
    }

    public void setUserLogged(boolean userLogged) {
        this.userLogged.set(userLogged);
    }

    public ObservableList<Contact> getUiContacts() {
        return uiContacts.get();
    }

    public ListProperty<Contact> uiContactsProperty() {
        return uiContacts;
    }

    public void setUiContacts(ObservableList<Contact> uiContacts) {
        this.uiContacts.set(uiContacts);
    }
}
