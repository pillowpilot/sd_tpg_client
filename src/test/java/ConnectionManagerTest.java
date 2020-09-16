import cliente.TCPConnectionManager;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

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
}
