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
    	String serverType;

    	
    	// Create TreeMap of servers for <Core Count, Type>
    	Map<Integer, String> serverInfo = new HashMap<>();
    	// Add each unique server type (in order from server)
    	for(Server s : servers) {
    		serverInfo.putIfAbsent(s.coreCount, s.type);
    	}
    	
    	Set set = serverInfo.entrySet();
    	Iterator iterator = set.iterator();
    	
    	serverInfo.size();
    	// for(int i=0; i<serverInfo.size(); i++)
    	
    	// For each server type
    	while(iterator.hasNext()) {
    		Map.Entry currentEntry = (Map.Entry)iterator.next();
    		serverType = (String) currentEntry.getValue();
//			jobScheduler.sendMessage("RESC" + serverType);
    	}
    	
    	
    	
    	
		return null;    	

    }

    //TODO: Sophie's Implementation
    public Server firstFit(Job j){


        return null;
    }
}
