package messages.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnregisterPayload extends Payload {
    @JsonProperty("username")
    private String username;

    public UnregisterPayload() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void accept(RequestVisitor visitor, Request request) {
        visitor.visitUnregister(request, this);
    }

    @Override
    public String toString() {
        return "UnregisterPayload{" +
                "username='" + username + '\'' +
                '}';
    }
}
