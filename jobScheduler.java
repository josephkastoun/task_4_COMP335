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
			Boolean newReq = true;

			sendMessage("HELO\n");
			recieveMessage();

			sendMessage("AUTH comp335\n");
			recieveMessage();			

			sendMessage("REDY\n");
			recieveMessage();


			while (true) {
				//Check if we've requested the servers before
				if(newReq) {
					reqAll();
					newReq = false;
				} else {
					//Send the Message to collect servers
					sendMessage("OK\n");
					//Server is this string
					String r = recieveMessage();

					//No more servers, lets break
					if(r.equals(".")){                     
						break;
					}

					//Init server Object
					Server s = new Server(r);
					//Add server object to List
					servers.add(s);
				}
			}

			while (true) {

				sendMessage("REDY\n");
				String r = recieveMessage();

				if(r.equals("NONE")) {
					break;
				}

				Job j = new Job(r);
				jobs.add(j);

				sendMessage("SCHD " + j.jobID + " " + getLargestServer(servers) + "0\n");
				recieveMessage();

			}

//			sendMessage("OK\n");
//			recieveMessage();

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
				sendMessage("QUIT\n");
				recieveMessage();
				socket.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}


	static void sendMessage(String message) throws IOException {
		//Get output stream for sending data
		OutputStream outputStream = socket.getOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
		//Use a buffered writer so we dont have to send byte by byte
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		//Write the message
		bufferedWriter.write(message);
		//Send it to the server
		bufferedWriter.flush();

		//Print the server output
		System.out.println("Message sent to the server : "+message);
	}

	static String recieveMessage() throws IOException {
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		//Setup the buffered reader to take the input
		BufferedReader br = new BufferedReader(isr);
		//Read the line from the buffered reader
		String message = br.readLine();
		//Output to console
		System.out.println("Message received from the server : " +message);

		//Return the string for use within the program
		return message;
	}

	static void reqAll()  throws IOException{
		sendMessage("RESC All\n");
		recieveMessage();
	}

	static String getLargestServer(ArrayList<Server> sList) {

		//Largest server type is last in list
			int i = servers.size() - 1;
		//Add space to return string so the server can read it
			String returnStr = sList.get(i).type +" ";
			return returnStr;

	}
}