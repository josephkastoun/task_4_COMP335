import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class jobScheduler
{

	private static Socket socket;
	private static ObjectOutputStream oos = null;
	private static ObjectInputStream ois = null;
	private static ArrayList<Job> jobs = null;
	public static long startTime = 0;
	public static Servers Servers;


	public static void main(String args[])
	{
		try
		{
			String host = "localhost";
			int port = 8096;
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);
			jobs = new ArrayList<>();
			Boolean newReq = true;

			//Initial Setup
			
				//Send message to connect to server
				sendMessage("HELO\n");
				recieveMessage();
	
				//Send authentication message to server
				sendMessage("AUTH comp335\n");
				recieveMessage();			
	
				//Send ready message to start receiving job and server info
				sendMessage("REDY\n");
				recieveMessage();
				startTime = System.currentTimeMillis();

				Servers = new Servers(new ArrayList<Server>());

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
					Servers.addServer(s);
				}
			}

			while (true) {
				//Send message to receive job info
				sendMessage("REDY\n");
				String r = recieveMessage();

				//If server responds with NONE (i.e there are no more jobs) break
				if(r.equals("NONE")) {
					break;
				}
				
				//Create a new job item with info given from server and add it to jobs arraylist
				Job j = new Job(r);
				jobs.add(j);

				//Send a scheduling message to the server with the most recent jobID to the largest given server
				if(args[0].equals("-a")){
					switch (args[1]){
						case "ff":
							Servers.firstFit(j).scheduleJob(j);
							break;
						case "bf":
							Servers.bestFit(j).scheduleJob(j);
							break;
						case "wf":
							Servers.worstFit(j).scheduleJob(j);
							break;
					}
				}else{
					Servers.getLargestServer().scheduleJob(j);
				}
			}



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
		//Use a buffered writer so we don't have to send byte by byte
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		//Write the message
		bufferedWriter.write(message);
		//Send it to the server
		bufferedWriter.flush();

		//Print the server output
		//System.out.println("Message sent to the server : "+message);
	}

	static String recieveMessage() throws IOException {
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		//Setup the buffered reader to take the input
		BufferedReader br = new BufferedReader(isr);
		//Read the line from the buffered reader
		String message = br.readLine();
		//Output to console
		//System.out.println("Message received from the server : " +message);

		//Return the string for use within the program
		return message;
	}

	static void reqAll()  throws IOException{
		sendMessage("RESC All\n");
		recieveMessage();
	}

}