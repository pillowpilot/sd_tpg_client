import java.io.IOException;
import java.net.*;
import java.util.ArrayDeque;
import java.util.Queue;

public class UDPDummyServer implements Runnable{
    public static final Integer MAX_BUFFER_SIZE = 2048;
    private Boolean keepRunning;
    private Integer portToListen;
    private Queue<String> queueOfRequests;
    private Queue<String> queueOfResponses;
    private DatagramSocket socket;
    private byte[] buffer;

    public UDPDummyServer(Integer portToListen) {
        this.portToListen = portToListen;
        this.buffer = new byte[MAX_BUFFER_SIZE];
        this.queueOfResponses = new ArrayDeque<>();
        this.queueOfRequests = new ArrayDeque<>();
    }

    public void addResponse(String response) {
        queueOfResponses.add(response);
    }
    public Queue<String> getRequests() { return queueOfRequests; }

    @Override
    public void run() {
        try {
            startListening();

            String request;
            keepRunning = true;
            while( keepRunning ) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                request = new String(packet.getData(), 0, packet.getLength());
                queueOfRequests.add(request);

                String response = queueOfRequests.remove();
                buffer = response.getBytes();

                InetAddress address = packet.getAddress();
                Integer port = packet.getPort(); // This should be equals to this.portToListen
                packet = new DatagramPacket(buffer, buffer.length, address, port);

                socket.send(packet);

                if(queueOfResponses.isEmpty())
                    keepRunning = false;
            }

            stopListening();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void stop() { this.keepRunning = false; }

    private void startListening() throws IOException {
        socket = new DatagramSocket(portToListen);
    }

    private void stopListening() throws IOException {
        socket.close();
    }
}
