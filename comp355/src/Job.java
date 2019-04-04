public class Job {

    //TODO: Edit this class to fit actual structure, this one is wrong.

    public String type;
    public int minRunTime, maxRunTime, populationRate;

    public Job(String type, int minRunTime, int maxRunTime, int populationRate){
        this.type = type;
        this.minRunTime = minRunTime;
        this.maxRunTime = maxRunTime;
        this.populationRate = populationRate;
    }

    public Job(String inputString){

    }

    @Override
    public String toString(){
        return String.format("node: job attr: type=\"%s\" attr: minRunTime=\"%d\" attr: maxRunTime=\"%d\" attr: populationRate=\"%d\"",
                this.type, this.minRunTime, this.maxRunTime, this.populationRate);
    }

}
