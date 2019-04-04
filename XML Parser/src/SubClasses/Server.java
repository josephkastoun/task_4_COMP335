package SubClasses;

public class Server {

    public String type;
    public int limit, bootupTime, coreCount, memory, disk;
    public double hourlyRate;

    public Server(String type, int limit, int bootupTime, float hourlyRate, int coreCount, int memory, int disk){
        this.type = type;
        this.limit = limit;
        this.bootupTime = bootupTime;
        this.hourlyRate = hourlyRate;
        this.coreCount = coreCount;
        this.memory = memory;
        this.disk = disk;
    }

    @Override
    public String toString(){
        return String.format("node: server attr: type=\"%s\" attr: limit=\"%d\" attr: bootupTime=\"%d\" attr: hourlyRate=\"%.1f\" attr: coreCount=\"%d\" attr: memory=\"%d\" attr: disk=\"%d\"",
                this.type, this.limit, this.bootupTime, this.hourlyRate, this.coreCount, this.memory, this.disk);
    }
}
