package server.messageVisitors;

import messages.requests.*;
import messages.responses.Response;
import models.Contact;
import server.InMemoryDatabase;
import server.models.Client;

import java.util.ArrayList;
import java.util.List;

public class RequestProcessor implements RequestVisitor {
    private InMemoryDatabase inMemoryDatabase;

    public RequestProcessor(InMemoryDatabase inMemoryDatabase) {
        this.inMemoryDatabase = inMemoryDatabase;
    }

    @Override
    public void visitAvailableContacts(Request request, AvailableContactsPayload payload) {
        List<Contact> availableContacts = new ArrayList<>();
        System.out.println("in mem " + inMemoryDatabase.getListOfConnectedClients());
        for(Client client: inMemoryDatabase.getListOfConnectedClients())
            availableContacts.add(new Contact(client.getUsername()));

        messages.responses.AvailableContactsPayload responsePayload =
                new messages.responses.AvailableContactsPayload();
        responsePayload.setAvailableContacts(availableContacts);
        Response response = new Response();
        response.setState("0");
        response.setMessage("ok");
        response.setOperationType("1");
        response.setPayload(responsePayload);

        System.out.println("Answering with " + response);

        inMemoryDatabase.lastResponse = response;
    }

    @Override
    public void visitRegistrationAttempt(Request request, RegistrationAttemptPayload payload) {
        String candidateUsername = payload.getUsername();

        Response response = new Response();
        messages.responses.RegistrationAttemptPayload responsePayload =
                new messages.responses.RegistrationAttemptPayload();
        responsePayload.setUsername(candidateUsername);

        Boolean isAvailable = inMemoryDatabase.isThisUsernameAvailable(candidateUsername);
        System.out.println("+++++" + inMemoryDatabase.getListOfConnectedClients() + " --- " + isAvailable);
        if(isAvailable.booleanValue()){
            System.out.println("11111");
            response.setState("0");
            response.setMessage("ok");

            inMemoryDatabase.registerThisUsername(candidateUsername);
        }else{
            System.out.println("222222");
            response.setState("1");
            response.setMessage("Usuario no disponible.");
        }
        response.setOperationType("5");
        response.setPayload(responsePayload);
        System.out.println("=====" + response);
        inMemoryDatabase.lastResponse = response;
    }

    @Override
    public void visitUnregister(Request request, UnregisterPayload payload) {
        String username = payload.getUsername();

        inMemoryDatabase.unregisterThisUsername(username);
        System.out.println(inMemoryDatabase.getListOfConnectedClients());
    }


}
