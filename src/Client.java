import java.net.*;
import java.io.*;

public class Client {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;


    TextFrame textFrame;

    Client() throws IOException {
        socket = new Socket("127.0.0.1", 2390); // создаем сокет используя IP-адрес и порт сервера.
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        textFrame = new TextFrame(in,out);
    }

}