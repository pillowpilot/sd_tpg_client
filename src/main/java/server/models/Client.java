package server.models;

import java.util.Objects;

public class Client {
    public enum State {AVAILABLE, UNAVAILABLE};

    private Integer id;
    private String username;
    private State state;

    public Client(Integer id, String username) {
        this.id = id;
        this.username = username;
        this.state = State.AVAILABLE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", state=" + state +
                '}';
    }
}
