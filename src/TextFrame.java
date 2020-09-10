import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TextFrame extends JFrame {
    Thread fromServer;
    JTextField textField;
    JLabel label[];
    int kolvoStrok=17;
    DataInputStream in;
    DataOutputStream out;


    TextFrame(DataInputStream in, DataOutputStream out){
        this.in=in;
        this.out=out;
        setLocation(20,20);
        setSize(430, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(kolvoStrok+1, 0));
        textField = new JTextField();
        label = new JLabel[kolvoStrok];
        for (int i = 0; i < kolvoStrok; i++) {
            label[i] = new JLabel();
            add(label[i]);
        }
        add(textField);
        start();
    }

    public void start(){
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                String word;
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        word = textField.getText();
                        textField.setText(null);
                        try {
                            out.writeUTF("$"+word);
                            out.flush();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                                            }
                }
                return false;
            }
        });
        /// НЕ ЗАБЫТЬ!!!!!!!!!!! Так как взял из предыдущего кода оно понимает, коггда команда от сервера, юзера или тебя
        fromServer = new Thread(new Runnable() {
            @Override
            public void run() {
                String line;
                while (true) {
                    try {
                        line = in.readUTF();
                        write(line.charAt(0),line.substring(1));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        fromServer.start();
        setVisible(true);
    }


    /// НЕ ЗАБЫТЬ!!!!!!!!!!! Так как взял из предыдущего кода оно понимает, коггда команда от сервера, юзера или тебя
    public void write(char  command,String string) {
        for (int i = 0; i < label.length-1; i++) {
            label[i].setText(label[i + 1].getText());
            label[i].setHorizontalAlignment(label[i + 1].getHorizontalAlignment());
        }
//# - server; @-your ; $-users
        System.out.println(command);
        if (command == '#') {
            label[label.length-1].setHorizontalAlignment(SwingConstants.CENTER);
            System.out.println("Centre");
        } else if (command == '$') {
            label[label.length-1].setHorizontalAlignment(SwingConstants.LEFT);
            System.out.println("Left");
        } else if (command == '@') {
            label[label.length-1].setHorizontalAlignment(SwingConstants.RIGHT);
        }

        label[label.length-1].setText(string);
    }

}
