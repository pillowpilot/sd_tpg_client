package cliente;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Administra una conexión TCP con el servidor.
 * Se implementa en su propio hilo y recibe los mensajes en JSON para su envío.
 * @see <a href="https://www.baeldung.com/a-guide-to-java-sockets">Detalles sobre sockets</a>
 * @see <a href="https://www.baeldung.com/java-blocking-queue">Detalles sobre productor-consumidor</a>
 */
public class TCPConnectionManager implements Runnable{
    public static final Integer TO_SEND_QUEUE_TIMEOUT = 50; // In milliseconds.
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private BlockingQueue<String> pendingMessagesToSend;
    private BlockingQueue<String> pendingMessagesToReceive;
    private Boolean keepRunning;
    private String ip;
    private Integer port;

    /**
     * Inicializa los campos del objeto. // TODO Improve one-liner.
     * @param ip Dirección de ip del servidor, p.e. "localhost".
     * @param port Puerto en el cual el servidor escucha.
     * @param pendingMessagesToSend Cola donde de los mensajes en formato json a enviar al servidor.
     * @param pendingMessagesToReceive Cola donde se recibe los mensajes json desde el servidor.
     */
    public TCPConnectionManager(@NotNull String ip, @NotNull Integer port, // TODO Better type for ip
                                @NotNull BlockingQueue<String> pendingMessagesToSend,
                                @NotNull BlockingQueue<String> pendingMessagesToReceive) {
        this.ip = ip;
        this.port = port;
        this.pendingMessagesToSend = pendingMessagesToSend;
        this.pendingMessagesToReceive = pendingMessagesToReceive;
    }

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
            System.out.println("Connection established.");

            keepRunning = true;
            while (keepRunning || !pendingMessagesToSend.isEmpty()) {

                String json_message;

                // Try to send a datagram or timeout after this.TO_SEND_QUEUE_TIMEOUT ms.
                if ((json_message = pendingMessagesToSend.poll(TO_SEND_QUEUE_TIMEOUT, TimeUnit.MILLISECONDS)) != null) {
                    out.println(json_message);
                }

                Thread.sleep(50);


                String receivedMessage = in.readLine(); // Blocking // TODO Find an non-blocking reading method!
                if(receivedMessage == null)
                    keepRunning = false;
                else {
                    System.out.println("In client: received " + receivedMessage);
                    pendingMessagesToReceive.add(receivedMessage);
                }
            
            }

            stopConnection();
            System.out.println("Connection ended.");
        }catch (IOException | InterruptedException ex) { // TODO Find a better way to deal with exceptions
            ex.printStackTrace();
        }
    }

    /**
     * Indica al objeto que debe finalizar su hilo.
     */
    public synchronized void stop() { this.keepRunning = false; }

    private void startConnection() throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void stopConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
