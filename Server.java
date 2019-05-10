import java.io.IOException;

public class Server {

    enum serverType{
        TINY,
        SMALL,
        MEDIUM,
        LARGE,
        XLARGE,
        XXLARGE,
        XXXLARGE,
        XXXXLARGE
    }

    public String type;
    public int id, state, availableTime, coreCount, memory, disk;
    public long completionTime;
    public serverType sType;
    private final int tolerance = 200;

    public Server(String type,int id, int state, int availableTime, int coreCount, int memory, int disk){
        this.type = type;
        this.id = id;
        this.state = state;
        this.availableTime = availableTime;
        this.coreCount = coreCount;
        this.memory = memory;
        this.disk = disk;

        switch (type){
            case "tiny":
                sType = serverType.TINY;
            case "small":
                sType = serverType.SMALL;
            case "large":
                sType = serverType.LARGE;
            case "xlarge":
                sType = serverType.XLARGE;
            case "2xlarge":
                sType = serverType.XXLARGE;
            case "3xlarge":
                sType = serverType.XXXLARGE;
            case "4xlarge":
                sType = serverType.XXXXLARGE;
        }
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


        switch (type){
            case "tiny":
                sType = serverType.TINY;
                break;
            case "small":
                sType = serverType.SMALL;
                break;
            case "medium":
                sType = serverType.MEDIUM;
                break;
            case "large":
                sType = serverType.LARGE;
                break;
            case "xlarge":
                sType = serverType.XLARGE;
                break;
            case "2xlarge":
                sType = serverType.XXLARGE;
                break;
            case "3xlarge":
                sType = serverType.XXXLARGE;
                break;
            case "4xlarge":
                sType = serverType.XXXXLARGE;
                break;
        }

    }

    public int fitness(Job j){
        return this.coreCount - j.numCPUCores;
    }
    
    public boolean isAvailable(){
        return completionTime <= System.currentTimeMillis();
    }

    public String scheduleJob(Job j) throws IOException {
        jobScheduler.sendMessage("SCHD " + j.jobID + " " + this.type + " " + this.id + " \n");
        String s = jobScheduler.recieveMessage();
        this.completionTime = System.currentTimeMillis() + (j.estRunTime * 1000) + tolerance;
        return s;
    }

    @Override
    public String toString(){
        //Output test
        return String.format("server: type=%s id=%d state=%d availableTime=%d coreCount=%d memory=%d disk=%d", type, id, state, availableTime, coreCount, memory, disk);
    }
}
