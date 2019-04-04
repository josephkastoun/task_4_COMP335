package SubClasses;

import java.util.ArrayList;

public class Config {
    private ArrayList<Job> Jobs;
    private ArrayList<Server> Servers;
    private ArrayList<TerminationCondition> TerminationConditions;
    private Workload Workload;
    private int randomSeed;

    public int getRandomSeed() {
        return randomSeed;
    }

    public ArrayList<Job> getJobs() {
        return Jobs;
    }

    public void addJob(Job j) {
        Jobs.add(j);
    }

    public ArrayList<Server> getServers() {
        return Servers;
    }

    public void addServer(Server s) {
        Servers.add(s);
    }

    public ArrayList<TerminationCondition> getTerminationConditions() {
        return TerminationConditions;
    }

    public void addTerminationConditions(TerminationCondition t) {
        TerminationConditions.add(t);
    }

    public SubClasses.Workload getWorkload() {
        return Workload;
    }

    public void setWorkload(SubClasses.Workload workload) {
        Workload = workload;
    }

    public Config(int randomSeed){
        this.randomSeed = randomSeed;
        Jobs = new ArrayList<>();
        Servers = new ArrayList<>();
        TerminationConditions = new ArrayList<>();
        Workload = null;
    }


    public void printConfig(){
        System.out.println(String.format("node: config attr: randomSeed=\"%d\"", getRandomSeed()));

        System.out.println("node: servers");
        getServers().forEach(n -> System.out.println(n.toString()));

        System.out.println("node: jobs");
        getJobs().forEach(n -> System.out.println(n.toString()));

        System.out.println(getWorkload().toString());

        System.out.println("node: termination");
        getTerminationConditions().forEach(n -> System.out.println(n.toString()));
    }
}
