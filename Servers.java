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


    public void addServer(Server s){
        servers.add(s);
    }


    //TODO: Joseph's Implementation
    public Server bestFit(Job j){


        return null;
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
    		
    		
    		
    		//resc server info
    		//Job job = new Job();
    		
    		for(int i=0; i<serverTypes; i++) {
    			for(int j=0; j<serverTypeCount; i++) {
    				if(job.numCPUCores <= serverVorVount) {
    						fitVal = job.numCPUCores - serverCoreCount;
    						
    				}
    			}
    		}
    	}	
    	
		return null;    	

    }

    //TODO: Sophie's Implementation
    public Server firstFit(Job j){


        return null;
    }
}
