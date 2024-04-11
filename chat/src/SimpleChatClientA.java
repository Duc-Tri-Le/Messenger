import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;

public class SimpleChatClientA {
    JPanel incoming;
    JTextField outcoming;
    JButton send;
    BufferedReader reader;
    Socket sock;
    PrintWriter writer;
    JButton decryptMessage;

    public static void main(String[] args) {
        SimpleChatClientA chat = new SimpleChatClientA();
        chat.go();
    }

    JFrame frame = new JFrame("Ludicrously Simple Chat Client");

    void go() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        decryptMessage = new JButton("decryptMessage");
        decryptMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                for (int i = 0; i < incoming.getComponentCount(); i++) {
                    JPanel t = (JPanel) incoming.getComponent(i);
                    JLabel label = (JLabel) t.getComponent(0);
                    try {
                        label.setText(mahoagiaima.decryptMessage(label.getText(), "0123456789abcdef"));
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }
            }
        });
        incoming = new JPanel();
        incoming.setPreferredSize(new Dimension(200, 300));
        incoming.setBackground(Color.white);
        incoming.setLayout(new BoxLayout(incoming, BoxLayout.Y_AXIS));
        JScrollPane qscrollpane = new JScrollPane(incoming);
        qscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outcoming = new JTextField(20);
        outcoming.addActionListener(new MyenterListener());
        send = new JButton("Send");
        send.addActionListener(new MysendListener());

        JPanel mainPanel = new JPanel();
        mainPanel.add(qscrollpane);

        mainPanel.add(outcoming);
        mainPanel.add(send);
        mainPanel.add(decryptMessage);
        setupNetworking();
        Thread t = new Thread(new Recieve());
        t.start();
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    void setupNetworking() {
        try {
            sock = new Socket("127.0.0.1", 5000);
            InputStreamReader income = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(income);
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("networking established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    class MyresetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            incoming.removeAll();
        }
    }

    class Recieve implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read  " + message);
                    JPanel text = new JPanel();
                    text.setPreferredSize(new Dimension(150, 200));
                    JLabel content = new JLabel(message);
                    text.add(content);
                    text.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // TODO Auto-generated method stub
                            try {

                                content.setText(mahoagiaima.decryptMessage(content.getText(), "0123456789abcdef"));
                            } catch (Exception e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            // TODO Auto-generated method stub
                        }
                    });
                    incoming.add(text);
                    incoming.setBackground(Color.white);
                    incoming.revalidate();
                    incoming.repaint();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class MysendListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Send();
        }
    }

    class MyenterListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            Send();
        }
    }

    public void Send() {
        try {
            writer.println(mahoagiaima.encryptMessage(outcoming.getText(), "0123456789abcdef"));
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        outcoming.setText("");
        outcoming.requestFocus();
    }

}