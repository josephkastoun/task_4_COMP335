import java.util.ArrayList;
import java.util.List;

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
    public Server worstFit(Job j){


        return null;
    }

    //TODO: Sophie's Implementation
    public Server firstFit(Job j){


        return null;
    }
}
