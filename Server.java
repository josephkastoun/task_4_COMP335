import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {


    public String type;
    public int id, state, availableTime, coreCount, memory, disk;
    public int flagCores, flagMemory, flagDisk;
    public long completionTime;
    public final int tolerance = 200;
    private boolean beingFlagged = false;
    public ConcurrentLinkedQueue<serverFlag> jobs;


    public Server(String type,int id, int state, int availableTime, int coreCount, int memory, int disk){
        this.type = type;
        this.id = id;
        this.state = state;
        this.availableTime = availableTime;
        this.coreCount = coreCount;
        this.memory = memory;
        this.disk = disk;
        flagCores = 0;
        flagDisk = 0;
        flagMemory = 0;
    }


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
        jobs = new ConcurrentLinkedQueue<>();
    }

    public int fitness(Job j){
        return this.coreCount - j.numCPUCores;
    }

    public boolean isRunnableJob(Job j){
        return coreCount >= j.numCPUCores && j.numCPUCores + flagCores <= this.coreCount && j.memory + flagMemory < this.memory && j.disk + flagDisk < this.disk;
    }
    
    public boolean isAvailable(){
        return completionTime < System.currentTimeMillis();
    }

    public boolean canRun(Job j){
        return j.numCPUCores <= coreCount && j.memory <= memory && j.disk <= disk;
    }

    public String scheduleJob(Job j, Servers ss) throws IOException {
        //System.out.println(type + ": " + coreCount + " currentCores: " + (coreCount - flagCores) + " Job: " + j.numCPUCores);

        if(!isRunnableJob(j)){
            return "err";
        }

        jobScheduler.sendMessage("SCHD " + j.jobID + " " + this.type + " " + this.id + " \n");
        String s = jobScheduler.recieveMessage();


        jobs.add(new serverFlag(true, 0, j));
        completionTime = System.currentTimeMillis() + (j.estRunTime * 1000);


        ss.iterateServerType(this.type);
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
