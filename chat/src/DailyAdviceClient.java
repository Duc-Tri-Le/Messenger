import java.io.*;
import java.net.*;
public class DailyAdviceClient{
    public static void main(String[] args){
        DailyAdviceClient advice = new DailyAdviceClient();
        advice.go();
    }
    public void go(){
        try{
            Socket sock = new Socket("127.0.0.1",4242);
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            System.out.println("Today advice is: " + line);
            reader.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}