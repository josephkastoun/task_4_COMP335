import java.io.*;
import java.net.*;
 
public class jobScheduler
{
 
    private static Socket socket;
 
    public static void main(String args[])
    {
        try
        {
            String host = "localhost";
            int port = 8096;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
 
            //Send the message to the server
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(
            		outputStreamWriter);
 
            String messageString = "HELO";
 
            String sendMessage = messageString + "\n";
            bufferedWriter.write(sendMessage);
            bufferedWriter.flush();
            System.out.println("Message sent to the server : "+sendMessage);
 
            //Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            System.out.println("Message received from the server : " +message);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            //Closing the socket
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
