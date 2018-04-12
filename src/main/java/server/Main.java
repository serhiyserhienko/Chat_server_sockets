package server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServerImpl(8383);
            server.runServer();
        } catch (IOException | ChatServerException e) {
            e.printStackTrace();
        }
    }
}
