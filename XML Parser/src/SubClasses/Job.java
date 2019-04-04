package SubClasses;

public class Job {

    public String type;
    public int minRunTime, maxRunTime, populationRate;

    public Job(String type, int minRunTime, int maxRunTime, int populationRate){
        this.type = type;
        this.minRunTime = minRunTime;
        this.maxRunTime = maxRunTime;
        this.populationRate = populationRate;
    }

    @Override
    public String toString(){
        return String.format("node: job attr: type=\"%s\" attr: minRunTime=\"%d\" attr: maxRunTime=\"%d\" attr: populationRate=\"%d\"",
                this.type, this.minRunTime, this.maxRunTime, this.populationRate);
    }

}
