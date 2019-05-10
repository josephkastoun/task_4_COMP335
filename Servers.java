import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;




public class Servers {


    public Servers(ArrayList<Server> s){
        this.servers = s;
    }

    private ArrayList<Server> servers = new ArrayList<>();

    public ArrayList<Server> getServers() {
        return servers;
    }

    public ArrayList<Server> getServersByType(Server.serverType s){
    	ArrayList<Server> sL = new ArrayList<>();

    	getServers().forEach(k -> {
    		if(k.sType == s){
    			sL.add(k);
			}
		});

    	return sL;
	}

    public void addServer(Server s){
        servers.add(s);
    }


    public Server bestFit(Job j){
		int bestFit = Integer.MAX_VALUE;
		int minAvail = Integer.MAX_VALUE;
		Server bfServer = null;

		for(Server.serverType sT : Server.serverType.values()){

			for(Server server : getServersByType(sT)){
				if(server.coreCount >= j.numCPUCores && server.isAvailable()){
					int fitness = server.fitness(j);
					if(fitness < bestFit && server.availableTime < minAvail){
						bestFit = fitness;
						minAvail = server.availableTime;
						bfServer = server;
					}
				}
			}
		}

		if(bfServer != null){
			return bfServer;
		}else{
			for(Server.serverType sT : Server.serverType.values()){

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
		}

        return bfServer;
    }
    
    

    //TODO: James' Implementation
    public Server worstFit(Job job) throws IOException{
    		
    	int worstFit = Integer.MIN_VALUE;
    	int altFit = Integer.MIN_VALUE;  
    	int tempCount;
    	int fitVal;

    	
    	// Create HashMap of servers for <Type, Core Count>
    	Map<String, Integer> serverInfo = new HashMap<>(); 
    	// Create HashMap of servers for <Type, Server Count>
    	Map<String, Integer> serverCount = new HashMap<>();
    	
    	// If first instance of server type, add to serverInfo map
    	for(Server s : servers) {
    		if(!serverInfo.containsKey(s.type)) {
    			serverInfo.put(s.type, s.coreCount);
    			serverCount.put(s.type, 1);
    	// If server already in serverInfo, increment count
    		} else {
    			tempCount = serverCount.get(s.type);
    			serverCount.put(s.type, tempCount);
    		}
    		
    		
    		/*
    		//resc server info
    		//Job job = new Job();
    		
    		for(int i=0; i<serverTypes; i++) {
    			for(int j=0; j<serverTypeCount; i++) {
    				if(job.numCPUCores <= serverVorVount) {
    						fitVal = job.numCPUCores - serverCoreCount;
    						
    				}
    			}
    		}

    		 */
    	}	
    	
		return null;    	

    }

    //TODO: Sophie's Implementation
    public Server firstFit(Job j){


        return null;
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
