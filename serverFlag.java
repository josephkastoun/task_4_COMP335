public class serverFlag{
    public boolean flag;
    public long time;
    public Job job;

    public serverFlag(boolean flag, long time, Job job){
        this.flag = flag;
        this.time = time;
        this.job = job;
    }

    public void doFlag(Server s){
        if(flag){
            s.flagMemory += job.memory;
            s.flagDisk += job.disk;
            s.flagCores += job.numCPUCores;
            s.jobs.add(new serverFlag(false, System.currentTimeMillis() + (job.estRunTime) + s.tolerance, job));
        }else{
            s.flagMemory -= job.memory;
            s.flagDisk -= job.disk;
            s.flagCores -= job.numCPUCores;
        }
    }

}
