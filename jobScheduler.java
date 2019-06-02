import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class jobScheduler
{

	private static Socket socket;
	private static ArrayList<Job> jobs = null;
	private static long startTime = 0;
	private static Servers Servers;


	public static void main(String args[]) {
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

				Servers = new Servers(new ArrayList<>());

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

			Servers.organise();

			while (true) {
				//Send message to receive job info
				sendMessage("REDY\n");
				String r = recieveMessage();

				//If server responds with NONE (i.e there are no more jobs) break
				if(r == null || r.equals("NONE")) {
					break;
				}
				
				//Create a new job item with info given from server and add it to jobs arraylist
				Job j = new Job(r);
				jobs.add(j);

				//Send a scheduling message to the server with the most recent jobID to the largest given server
                if (args.length > 0 && args[0].equals("-a")) {
                    if(args[1].equalsIgnoreCase("ff")) {
                        Servers.firstFit(j).scheduleJob(j);
                    }
                    if(args[1].equalsIgnoreCase("wf")) {
                        Servers.worstFit(j).scheduleJob(j);
                    }
                    if(args[1].equalsIgnoreCase("bf")) {
                        Servers.bestFit(j).scheduleJob(j);
                    }
                    if(args[1].equalsIgnoreCase("mc")) {
                        Servers serv = getAvailServers(j);
                        Server minServer = Servers.minCost(j);
                        Server selectedServer = serv.minCost(j);
                        if(serv.getServers().size() == 0 || selectedServer == null|| !minServer.type.equalsIgnoreCase(selectedServer.type)){
                            if(selectedServer == null) {
                                minServer.scheduleJob(j);
                                break;
                            }
                        }
                        selectedServer.scheduleJob(j);
                    }
                    if(args[1].equalsIgnoreCase("mu")) {
                            Servers.maxUtilisation(j).scheduleJob(j);
                    }
                }
                else {
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

	static Servers getAvailServers(Job j) throws IOException {
		ArrayList<Server> s = new ArrayList<>();

		sendMessage(String.format("RESC Avail %d %d %d\n", j.numCPUCores, j.memory, j.disk));
		recieveMessage();
		while (true) {
			//Send the Message to collect servers
			sendMessage("OK\n");
			//Server is this string
			String r = recieveMessage();

			//No more servers, lets break
			if(r.equals(".")){
				break;
			}

			if(r.equalsIgnoreCase("data"))
				continue;

			//Init server Object
			s.add(new Server(r));
		}
		Servers ss = new Servers(s);
		//ss.printAll();

		return new Servers(s);

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

		//System.out.println("Message received from the server : "+message);

		//Return the string for use within the program
		return message;
	}

	private static void reqAll()  throws IOException{
		sendMessage("RESC All\n");
		recieveMessage();
	}

}