package server;

import server.connectionManagers.UDPListener;
import server.messageVisitors.RequestProcessor;

import java.net.SocketException;

public class Server {
    public static void main(String[] args) throws SocketException {
        InMemoryDatabase inMemoryDatabase = new InMemoryDatabase();
        RequestProcessor requestProcessor = new RequestProcessor(inMemoryDatabase);

        UDPListener udpListener = new UDPListener(9999, inMemoryDatabase, requestProcessor,
                "serverside-udplistener");
        udpListener.start();



        //udpListener.interrupt();
    }
}
