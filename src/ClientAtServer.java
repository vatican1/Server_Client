import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientAtServer {
    DataInputStream in;
    DataOutputStream out;
    Socket socket;
    SendAll sendAll;
    int id;

    ClientAtServer(Socket socket, SendAll sendAll,int id) throws IOException {
        this.id=id;
        this.socket=socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        this.sendAll = sendAll;
    }
    public void send(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }
    public void start() throws IOException {

        send("#Введите ваше имя");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = null;
                        message = in.readUTF();
                        sendAll.send(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

}
