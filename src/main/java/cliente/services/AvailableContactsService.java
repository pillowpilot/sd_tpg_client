package cliente.services;

import cliente.UDPListaContactos;
import cliente.models.ClienteDTO;
import cliente.models.Contact;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                    UDPListaContactos udpListaContactos = new UDPListaContactos(serverPort, serverIp);
                    ArrayList<ClienteDTO> clienteDTOs = udpListaContactos.call();
                    List<Contact> contacts = clienteDTOs.stream()
                            .map((ClienteDTO dto) -> new Contact(dto.getUsername()))
                            .collect(Collectors.toList());

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
