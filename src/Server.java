import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class Server  implements SendAll {
    LinkedList<ClientAtServer> clientsAtServer= new LinkedList<ClientAtServer>();
    ServerSocket ss;

    Server(int socketNumber) throws IOException {
        System.out.println("Server is ready to start");
        ss = new ServerSocket(socketNumber);
    }

    public void start() throws IOException {
        System.out.println("No client");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Socket socket = null;
                    try {
                        socket = ss.accept();
                        clientsAtServer.add(new ClientAtServer(socket, Server.this::send,clientsAtServer.size()));
                        clientsAtServer.getLast().start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Client had been added");

                }
            }
        });
        thread.start();
    }
    @Override
    public void send(String string) throws IOException {
        System.out.println(string);
        for (int i = 0; i < clientsAtServer.size(); i++) {
            clientsAtServer.get(i).send(string);
        }
    }



}