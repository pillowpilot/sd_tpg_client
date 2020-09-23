package cliente;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class UDPConnectionManager implements Runnable{
    public static final Integer BUFFER_SIZE = 1024; // TODO set bufsize as param
    public static final Integer SOCKET_TIMEOUT = 50; // In milliseconds.
    public static final Integer TO_SEND_QUEUE_TIMEOUT = 50; // In milliseconds.
    private String name;
    private String remoteIp;
    private Integer localListeningPort;
    private Integer localSendingPort;
    private Integer remoteListeningPort;
    private DatagramSocket sendingSocket;
    private DatagramSocket listeningSocket;
    private byte[] buffer;
    private BlockingQueue<String> pendingMessagesToSend;
    private BlockingQueue<String> pendingMessagesToReceive;
    private Boolean keepRunning;

    /**
     * Inicializa los campos del objeto. // TODO Improve one-liner.
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/net/DatagramSocket.html">Detalles sobre DatagramSocket</a>
     * @param remoteIp Dirección de ip del servidor, p.e. "localhost" o "50.50.50.50".
     * @param localListeningPort Puerto en el cual el servidor escucha.
     * @param pendingMessagesToSend Cola donde de los mensajes en formato json a enviar al servidor.
     * @param pendingMessagesToReceive Cola donde se recibe los mensajes json desde el servidor.
     */
    public UDPConnectionManager(@NotNull String remoteIp, // TODO Better type for ip
                                @NotNull Integer localSendingPort, @NotNull Integer localListeningPort, @NotNull Integer remoteListeningPort,
                                @NotNull BlockingQueue<String> pendingMessagesToSend,
                                @NotNull BlockingQueue<String> pendingMessagesToReceive) {
        this.remoteIp = remoteIp;
        this.localListeningPort = localListeningPort;
        this.localSendingPort = localSendingPort;
        this.remoteListeningPort = remoteListeningPort;
        this.pendingMessagesToSend = pendingMessagesToSend;
        this.pendingMessagesToReceive = pendingMessagesToReceive;
        this.buffer = new byte[BUFFER_SIZE];
    }

    public void setName(String name) { this.name = name;}

    public void addJSONMessageToSendingQueue(@NotNull String jsonMessage) {
        pendingMessagesToSend.add(jsonMessage);
    }

    public @NotNull String retrieveJSONMessageFromReceivingQueue() throws InterruptedException {
        return pendingMessagesToReceive.take(); // Blocks until there is something to retrieve
    }

    @Override
    public void run() {
        try {
            startConnection();

            keepRunning = true;
            while (keepRunning || !pendingMessagesToSend.isEmpty()) {

                String json_message;

                // Try to send a datagram or timeout after this.TO_SEND_QUEUE_TIMEOUT ms.
                if ((json_message = pendingMessagesToSend.poll(TO_SEND_QUEUE_TIMEOUT, TimeUnit.MILLISECONDS)) != null) {
                    buffer = json_message.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
                            InetAddress.getByName(remoteIp), remoteListeningPort);
                    sendingSocket.send(packet);
                }

                Thread.sleep(50);

                // Try to receive a datagram or timeout after this.SOCKET_TIMEOUT ms.
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    listeningSocket.receive(packet); // This method blocks until a datagram is received or timeout.
                    json_message = new String(packet.getData(), 0, packet.getLength());
                    pendingMessagesToReceive.add(json_message);
                }catch (SocketTimeoutException ex) {

                }
            }

            stopConnection();
        }catch (IOException | InterruptedException ex) { // TODO Find a better way to deal with exceptions
            ex.printStackTrace();
        }
    }

    /**
     * Indica al objeto que debe finalizar su hilo.
     */
    public synchronized void stop() { this.keepRunning = false; }

    private void startConnection() throws IOException {
        this.sendingSocket = new DatagramSocket(localSendingPort); // DatagramSocket(port) binds to port, which is LOCAL.
        this.sendingSocket.setSoTimeout(SOCKET_TIMEOUT);

        this.listeningSocket = new DatagramSocket(localListeningPort); // DatagramSocket(port) binds to port, which is LOCAL.
        this.listeningSocket.setSoTimeout(SOCKET_TIMEOUT);
    }

    private void stopConnection() throws IOException {
        sendingSocket.close();
        listeningSocket.close();
    }
}
