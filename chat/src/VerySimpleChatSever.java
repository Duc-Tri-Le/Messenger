import java.io.*;
import java.net.*;
import java.util.*;
public class VerySimpleChatSever {
    ArrayList <Object> clientOutputStreams;
    public static void main(String[] args){
        new VerySimpleChatSever().go();
    }
    public void go(){
        clientOutputStreams = new ArrayList<Object>();
        try
        {
            ServerSocket server = new ServerSocket(5000);
            while(true)
            {
                Socket clientSocket = server.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connection");
            }
        }
        catch(Exception ex){ex.printStackTrace();}
    }
    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;
        public ClientHandler(Socket clientSocket){
            try {
            sock = clientSocket;
            InputStreamReader in = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(in);
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        public void run(){
            String message;
            try{
                while((message = reader.readLine()) !=null)
                {
                    System.out.println("read " + message);
                    tellEveryone(message);
                }
            }
            catch(Exception ex){ex.printStackTrace();}
        }
    }
    void tellEveryone(String mes){
        Iterator <Object> it = clientOutputStreams.iterator();
        while(it.hasNext())
        {
            try {
                PrintWriter writer = (PrintWriter)it.next();
                writer.println(mes);
                writer.flush();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
