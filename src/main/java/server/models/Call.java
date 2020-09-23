package server.models;

public class Call {
    private Client clientA;
    private Client clientB;

    public Call(Client clientA, Client clientB) {
        this.clientA = clientA;
        this.clientB = clientB;
    }

    public Client getClientA() {
        return clientA;
    }

    public Client getClientB() {
        return clientB;
    }
}
