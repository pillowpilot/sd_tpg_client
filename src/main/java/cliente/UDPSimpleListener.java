package cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPSimpleListener extends Thread {
    public static final Integer BUFFER_SIZE = 1024;
    private final DatagramSocket listeningSocket;

    public UDPSimpleListener(Integer listeningPort, String threadName) throws SocketException {
        super(threadName);
        this.listeningSocket = new DatagramSocket(listeningPort);
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            try {

                byte[] buffer = new byte[BUFFER_SIZE];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                listeningSocket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println(">> " + message);

            }catch (IOException exception) {
                exception.printStackTrace();
                this.interrupt();
            }
        }
        listeningSocket.close();
    }
}
