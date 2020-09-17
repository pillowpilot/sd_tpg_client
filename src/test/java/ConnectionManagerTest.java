import cliente.TCPConnectionManager;
import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConnectionManagerTest {
    @Test
    public void testConnectionWithoutMessages() {
        Integer port = 9999;

        DummyServer server = new DummyServer(port);
        Thread serverThread = new Thread(server);
        serverThread.start();

        BlockingQueue<String> messagesToSend = new LinkedBlockingDeque<>();
        BlockingQueue<String> messagesToReceive = new LinkedBlockingDeque<>();
        TCPConnectionManager client = new TCPConnectionManager("localhost", port, messagesToSend, messagesToReceive);
        Thread clientThread = new Thread(client);
        clientThread.start();

        client.stop();
        server.stop();
    }

    @Test
    public void testRequestReply() throws InterruptedException {
        Integer port = 9999;

        DummyServer server = new DummyServer(port);
        Queue<String> receivedRequests = server.getRequests();
        String response1 = "AnotherRandomString";
        server.addResponse(response1);
        String response2 = "YouKnowMyName";
        server.addResponse(response2);

        Thread serverThread = new Thread(server);
        serverThread.start();

        BlockingQueue<String> messagesToSend = new LinkedBlockingDeque<>();
        BlockingQueue<String> messagesToReceive = new LinkedBlockingDeque<>();
        TCPConnectionManager client = new TCPConnectionManager("localhost", port, messagesToSend, messagesToReceive);
        Thread clientThread = new Thread(client);
        clientThread.start();

        String request1 = "SomeRandomString";
        client.addJSONMessageToSendingQueue(request1);

        String request2 = "TestingInJavaIsFun";
        client.addJSONMessageToSendingQueue(request2);

        client.stop();
        server.stop();

        clientThread.join();
        serverThread.join();

        assertEquals(response1, client.retrieveJSONMessageFromReceivingQueue());
        assertEquals(response2, client.retrieveJSONMessageFromReceivingQueue());

        assertEquals(request1, receivedRequests.poll());
        assertEquals(request2, receivedRequests.poll());
    }
}
