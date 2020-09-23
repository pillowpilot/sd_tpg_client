package server.connectionManagers;

import messages.requests.Request;
import messages.requests.RequestUnmarshaller;
import messages.responses.RegistrationAttemptPayload;
import messages.responses.Response;
import messages.responses.ResponseMarshaller;
import server.InMemoryDatabase;
import server.messageVisitors.RequestProcessor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPListener extends Thread {
    public static final Integer BUFFER_SIZE = 1024;
    private final DatagramSocket socket;
    private final InMemoryDatabase inMemoryDatabase;
    private final RequestProcessor requestProcessor;

    public UDPListener(Integer listeningPort,
                       InMemoryDatabase inMemoryDatabase, RequestProcessor requestProcessor,
                       String threadName) throws SocketException {
        super(threadName);
        this.inMemoryDatabase = inMemoryDatabase;
        this.requestProcessor = requestProcessor;
        this.socket = new DatagramSocket(listeningPort);
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            try {

                byte[] buffer = new byte[BUFFER_SIZE];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                Integer port = packet.getPort();

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println(">> " + message);

                RequestUnmarshaller requestUnmarshaller = new RequestUnmarshaller();
                Request request = requestUnmarshaller.fromJSON(message);

                request.accept(requestProcessor);
                Response response = inMemoryDatabase.lastResponse; // TODO Fix this!

                ResponseMarshaller marshaller = new ResponseMarshaller();
                String response_json_message = marshaller.toJSON(response);

                buffer = response_json_message.getBytes();
                packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);

            }catch (IOException exception) {
                exception.printStackTrace();
                this.interrupt();
            }
        }
        socket.close();
    }
}
