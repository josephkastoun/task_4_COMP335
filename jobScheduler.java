import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class jobScheduler
{

	private static Socket socket;
	private static ObjectOutputStream oos = null;
	private static ObjectInputStream ois = null;
	private static ArrayList<Server> servers;
	private static ArrayList<Job> jobs;


	public static void main(String args[])
	{
		try
		{
			String host = "localhost";
			int port = 8096;
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);
			servers = new ArrayList<>();
			jobs = new ArrayList<>();

			sendMessage("HELO\n");

			recieveMessage();

			sendMessage("AUTH comp335\n");

			recieveMessage();

			while (true) {
				sendMessage("REDY\n");
				String r = recieveMessage();
				System.out.println(r);
				
				if(r.equals("NONE")) {
					break;
				}
				
				Job j = new Job(r);
				System.out.println(j.toString());
				jobs.add(j);
				
//				System.out.println(j.jobID);
				
				sendMessage("SCHD " + j.jobID + " " + "4xlarge " + "0\n");
				recieveMessage();

			}
			
			
			sendMessage("RESC All\n");

			recieveMessage();

//			sendMessage("OK\n");
//			recieveMessage();
			

			while (true) {
				sendMessage("OK\n");
				String r = recieveMessage();

				if(r.equals(".")){                     
					break;
				}
				
				Server s = new Server(r);
				System.out.println(s.toString());
				servers.add(s);
			}
			
			sendMessage("OK");
			recieveMessage();

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


	static void sendMessage(String message) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		bufferedWriter.write(message);
		bufferedWriter.flush();
		System.out.println("Message sent to the server : "+message);
	}

	static String recieveMessage() throws IOException {
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String message = br.readLine();
		System.out.println("Message received from the server : " +message);

		return message;
	}
}