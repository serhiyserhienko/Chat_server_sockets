package server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SocketProcessor implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private List<SocketProcessor> clients;

    public SocketProcessor(Socket socketParam, List<SocketProcessor> clients) throws IOException {
        this.socket = socketParam;
        this.clients = clients;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
    }

    public void run() {
        try {
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    System.out.println(socket.getRemoteSocketAddress() + "/" + line);
                    synchronized (clients) {
                        for (SocketProcessor sp : clients) {
                            try {
                                sp.send(line);
                            } catch (Exception e) {
                                System.err.println("cant find this client");
                            }
                        }
                    }
                } else {
                    System.out.printf("CLIENT LOGOUT %s \n", socket.getRemoteSocketAddress());
                    clients.remove(this);
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void send(String line) throws IOException {
        writer.write(line);
        writer.write("\n");
        writer.flush();
    }
}
