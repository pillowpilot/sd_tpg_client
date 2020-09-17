import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;

public class DummyServer implements Runnable{
    private Boolean keepRunning;
    private Integer portToListen;
    private Queue<String> queueOfRequests;
    private Queue<String> queueOfResponses;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public DummyServer(Integer portToListen) {
        this.portToListen = portToListen;
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

            String line;
            keepRunning = true;
            while( keepRunning ) {
                if( (line = in.readLine()) == null ) // in.readline() != null while there is a connection & Blocks until request
                    keepRunning = false;

                queueOfRequests.add(line);

                String response = queueOfResponses.remove();
                out.println(response);

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
        serverSocket = new ServerSocket(portToListen);
        clientSocket = serverSocket.accept(); // Blocks until successful connection with client
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private void stopListening() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
