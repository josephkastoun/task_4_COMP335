import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class Server {


    public String type;
    public int id, state, availableTime, coreCount, memory, disk;
    public long completionTime;
    private final int tolerance = 200;

    public Server(String type,int id, int state, int availableTime, int coreCount, int memory, int disk){
        this.type = type;
        this.id = id;
        this.state = state;
        this.availableTime = availableTime;
        this.coreCount = coreCount;
        this.memory = memory;
        this.disk = disk;
    }

    public ConcurrentHashMap<Long, Job> jobs;

    public Server(String inputString){
        //Split the server object into individual elements
        String[] subStrings = inputString.split(" ");

        //Assign properties individually based on the substrings
        this.type = subStrings[0];
        this.id = Integer.parseInt(subStrings[1]);
        this.state = Integer.parseInt(subStrings[2]);
        this.availableTime = Integer.parseInt(subStrings[3]);
        this.coreCount = Integer.parseInt(subStrings[4]);
        this.memory = Integer.parseInt(subStrings[5]);
        this.disk = Integer.parseInt(subStrings[6]);
        jobs = new ConcurrentHashMap<>();
    }

    public int fitness(Job j){
        return this.coreCount - j.numCPUCores;
    }
    
    public boolean isAvailable(){
        return completionTime < System.currentTimeMillis();
    }

    public boolean isAvailable(int i){

        for(Long l : jobs.keySet()){
            if(l < System.currentTimeMillis()){
                coreCount += jobs.get(l).numCPUCores;
                memory += jobs.get(l).memory;
                jobs.remove(l);
            }
        }
        return true;
    }
    
    // If RECS ALL used, available time == -1 if server is available immediately
    public boolean isImmediatelyAvailable(){
    	return this.availableTime == -1;
    }

    public boolean canRun(Job j){
        return j.numCPUCores <= coreCount && j.memory <= memory && j.disk <= disk;
    }

    public String scheduleJob(Job j) throws IOException {

        jobScheduler.sendMessage("SCHD " + j.jobID + " " + this.type + " " + this.id + " \n");
        String s = jobScheduler.recieveMessage();
        coreCount -= j.numCPUCores;
        memory -= j.memory;
        jobs.put(System.currentTimeMillis() + (j.estRunTime * 1000) + tolerance, j);
        completionTime = System.currentTimeMillis() + (j.estRunTime * 1000);
        return s;
    }

    public int compareTo(Server other) {
        int c1 = this.coreCount;
        int c2 = other.coreCount;
        if(c1 < c2) return -1;
        if(c1 > c2) return 1;
        else return 0;
    }

    @Override
    public String toString(){
        //Output test
        return String.format("server: type=%s id=%d state=%d availableTime=%d coreCount=%d memory=%d disk=%d", type, id, state, availableTime, coreCount, memory, disk);
    }
}
