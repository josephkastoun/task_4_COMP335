import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Servers {


	public Servers(ArrayList<Server> s){
		this.servers = s;
		serverTypes = new ArrayList<>();
	}

	private ArrayList<Server> servers;

	public ArrayList<Server> getServers() {
		return servers;
	}

	public void addServer(Server s){
		servers.add(s);
	}

	public ArrayList<String> serverTypes;


	public Server bestFit(Job j){
		int bestFit = Integer.MAX_VALUE;
		int minAvail = Integer.MAX_VALUE;
		Server bfServer = null;

		for(String sT : serverTypes){

			for(Server server : getServersByType(sT)){
				if(server.coreCount >= j.numCPUCores){
					int fitness = server.fitness(j);
					if(fitness < bestFit && server.availableTime < minAvail){
						bestFit = fitness;
						minAvail = server.availableTime;
						bfServer = server;
					}
				}
			}
		}

		return bfServer;
	}

	//TODO: James' Implementation
	public Server worstFit(Job job){

		int worstFit = Integer.MIN_VALUE;
		int altFit = Integer.MIN_VALUE;  

		Server worstFitServer = null;
		Server altFitServer = null;


		for(String sT : serverTypes) {
			for(Server server :  getServersByType(sT)) {
				
				if(server.coreCount >= job.numCPUCores && server.isAvailable()) {
					int fitVal = server.fitness(job);

					if(fitVal > worstFit && server.isImmediatelyAvailable()) { //Immediately avail
						worstFit = fitVal;
						worstFitServer = server;
						
						//ADD Short definite amount of time instead of 1 V
					} else if (fitVal > altFit && server.availableTime < 1) {
						altFit = fitVal;
						altFitServer = server;
					}
				}
			}
		}
		
		if (worstFitServer != null) {
			return worstFitServer;
			
		} else if (altFitServer != null) {
			return altFitServer;
			
		} else {
			for(String sT : serverTypes) {
				for(Server server : getServersByType(sT)) {
					
					if(server.coreCount >= job.numCPUCores) {
						int fitVal = server.fitness(job);
						if(fitVal > worstFit && server.isImmediatelyAvailable()) {
							worstFit = fitVal;
							worstFitServer = server;
						}
					}
				}
			}
		}
		return worstFitServer;    	

	}

	public Server firstFit(Job job){
		ArrayList<Map.Entry<Server, Integer>> byType = countServersByType();
		ArrayList<Server> s;

		// loop through server types (smallest to largest)
		for(int i = 0; i <byType.size(); i++) {
			s = getServersByType(byType.get(i).getKey().type);
			// Loop through amount of servers of each type.
			for(int j =0; j<s.size(); j++) {
				// if server has sufficient available resources to run job return the server
				if(s.get(j).coreCount < job.numCPUCores)
					return s.get(j);
			}
		}
		// Return first active server with sufficient initial resource capacity to run job.
		return null;
	}

	public ArrayList<Map.Entry<Server, Integer>> countServersByType() {
		Map<Server, Integer> serverCount = new HashMap<>();

		// Create HashMap which counts occurrences of each Server Type.
		for(Server s : servers){
			int count = 0;
			if( serverCount.containsKey(s.type) ) {
				count = serverCount.get(s.type);
			}
			serverCount.put(s, count+1);
		}

		// Sort into an ordered ArrayList by comparing Core counts.
		Set<Map.Entry<Server, Integer>> entrySet = serverCount.entrySet();
		ArrayList<Map.Entry<Server, Integer>> serverBySize = new ArrayList<Map.Entry<Server,Integer>>(entrySet);
		bubbleSort(serverBySize);

		return serverBySize;
	}


	public void bubbleSort(ArrayList<Map.Entry<Server, Integer>> list) {
		for(int i=0; i<list.size(); i++) {
			for(int k=0; k<list.size()-1-i; k++) {
				Server s1 = list.get(k).getKey();
				Server s2 = list.get(k+1).getKey();
				if(s1.compareTo(s2) == 1) {
					Map.Entry<Server, Integer> temp = list.get(k);
					list.set(k, list.get(k+1));
					list.set(k+1, temp);
				}
			}
		}
	}

	public ArrayList<Server> getServersByType(String type){
		ArrayList<Server> list = new ArrayList<>();
		for(Server s: servers) {
			if(s.type.equalsIgnoreCase(type)) {
				list.add(s);
			}
		}
		return list;
	}

	public Server getLargestServer() {

		int curHighCoreCount = 0;
		Server largestServer = null;

		//Find largest server server
		for(int i = 0; i<getServers().size(); i++) {

			if (getServers().get(i).coreCount > curHighCoreCount){
				//System.out.println(sList.get(i).coreCount);
				curHighCoreCount = getServers().get(i).coreCount;
				largestServer = getServers().get(i);
				//System.out.println(largestServerType);

			}
		}
		//Add space to return string so the server can read it
		return largestServer;
	}
}
