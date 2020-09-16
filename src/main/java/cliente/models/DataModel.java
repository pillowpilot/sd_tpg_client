package cliente.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {
    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    public ObservableList<Contact> getContacts() {
        return contacts;
    }
}
