public class Job {

	public String type;
    public int submitTime, jobID, estRunTime, numCPUCores, memory, disk;

    public Job(int submitTime, int jobID, int estRunTime, int numCPUCores, int memory, int disk){
        this.submitTime = submitTime;
        this.jobID = jobID;
        this.estRunTime = estRunTime;
        this.numCPUCores = numCPUCores;
        this.memory = memory;
        this.disk = disk;
    }

    public Job(String inputString){
        //Split the Job into individual objects
        String[] subStrings = inputString.split(" ");

        //Assign each element based on the substrings
        this.type = subStrings[0];
        this.submitTime = Integer.parseInt(subStrings[1]);
        this.jobID = Integer.parseInt(subStrings[2]);
        this.estRunTime = Integer.parseInt(subStrings[3]);
        this.numCPUCores = Integer.parseInt(subStrings[4]);
        this.memory = Integer.parseInt(subStrings[5]);
        this.disk = Integer.parseInt(subStrings[6]);

    }

    @Override
    public String toString(){
        return String.format("node: job attr: type=\"%s\" attr: submitTime=\"%d\" attr: jobID=\"%d\" attr: estRunTime=\"%d\" attr: numCPUCores=\"%d\" attr: memory=\"%d\" attr disk=\"%d\"",
                this.type, this.submitTime, this.jobID, this.estRunTime, this.numCPUCores, this.memory, this.disk);
    }

}
