import cliente.UDPConnectionManager;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UDPConnectionManagerTest {
    @Test
    public void sendSimpleMessages() throws InterruptedException {
        final String ip = "localhost";
        final Integer producerSendingPort = 9999;
        final Integer producerListeningPort = 8888;
        final Integer consumerSendingPort = 7777;
        final Integer consumerListeningPort = 6666;

        BlockingQueue<String> toSend = new LinkedBlockingQueue<>();
        toSend.add("first message");
        toSend.add("second message");
        toSend.add("third message");
        UDPConnectionManager connManagerSideA = new UDPConnectionManager(ip,
                producerSendingPort, producerListeningPort, consumerListeningPort, toSend, new LinkedBlockingQueue<>()); // Producer
        connManagerSideA.setName("producer");

        BlockingQueue<String> toReceive = new LinkedBlockingQueue<>();
        UDPConnectionManager connManagerSideB = new UDPConnectionManager(ip,
                consumerSendingPort, consumerListeningPort, producerListeningPort, new LinkedBlockingQueue<>(), toReceive); // Consumer
        connManagerSideB.setName("consumer");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future connManagerFutureB = executorService.submit(connManagerSideB);
        Thread.sleep(1000);
        Future connManagerFutureA = executorService.submit(connManagerSideA);
        Thread.sleep(1000);
        connManagerSideA.addJSONMessageToSendingQueue("last minute message");
        Thread.sleep(1000);
        connManagerSideA.stop();
        connManagerSideB.stop();
        try {
            connManagerFutureA.get();
            connManagerFutureB.get();
        }catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        assertEquals(toReceive.size(), 4);
        assertTrue(toReceive.contains("first message"));
        assertTrue(toReceive.contains("second message"));
        assertTrue(toReceive.contains("third message"));
        assertTrue(toReceive.contains("last minute message"));

        System.out.println("toSend queue: " + toSend);
        System.out.println("toReceive queue: " + toReceive);
    }
}
