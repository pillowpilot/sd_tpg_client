package server;

import messages.responses.Response;
import server.models.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InMemoryDatabase {
    private List<Client> connectedClients;
    private List<Call> ongoingCalls;
    private Integer nextClientId;
    public Response lastResponse; // TODO Fix this!

    public InMemoryDatabase(){
        this.connectedClients = new ArrayList<>();
        this.ongoingCalls = new ArrayList<>();
        this.nextClientId = 0;
    }

    public Boolean isThisUsernameAvailable(String username) {
        for(Client c: connectedClients)
            if(c.getUsername().equals(username))
                return false;
        return true;
    }

    public Boolean isTheClientWithThisUsernameAvailable(String username) {
        for(Client c: connectedClients)
            if(c.getUsername().equals(username)) {
                for (Call call : ongoingCalls)
                    if (call.getClientA().equals(username) || call.getClientB().equals(username))
                        return false;
            }
        return false;
    }

    public void registerThisUsername(String username) { // Assume a previous verification already happen
        Client client = new Client(nextClientId, username);
        nextClientId++;
        connectedClients.add(client);
    }

    public void unregisterThisUsername(String username) {
        Iterator<Client> iter = connectedClients.iterator();
        while(iter.hasNext()){
            Client c = (Client) iter.next();
            if(c.getUsername().equals(username)){
                iter.remove();
                break;
            }
        }
    }

    public List<Client> getListOfConnectedClients() {
        return connectedClients;
    }
}
