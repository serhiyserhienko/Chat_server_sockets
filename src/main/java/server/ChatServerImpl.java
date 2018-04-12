package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServerImpl implements ChatServer {
    private ServerSocket serverSocket;
    private Thread socketThread;
    private List<SocketProcessor> clients = new ArrayList<>();
    private int port;

    public ChatServerImpl(int port) throws IOException {
        this.socketThread = Thread.currentThread();
        this.serverSocket = new ServerSocket(port);
        this.port = port;
    }

    @Override
    public void runServer() throws IOException {
        System.out.printf("Server is running on port %d... \n", port);
        while (true) {
            Socket socketClient = serverSocket.accept();
            SocketProcessor socketProcessorClient = new SocketProcessor(socketClient, clients);
            if (socketClient != null) {
                clients.add(socketProcessorClient);
                System.out.printf("CONNECTION MADE WITH %s \n", socketClient.getRemoteSocketAddress());
                Thread thread = new Thread(socketProcessorClient);
                thread.setDaemon(true);
                thread.start();
            }
        }
    }
}
