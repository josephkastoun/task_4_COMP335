package SubClasses;

public class Workload {
    public String type;
    public int limit, minLoad, maxLoad;

    public Workload(String type, int minLoad, int maxLoad){
        this.type = type;
        this.minLoad = minLoad;
        this.maxLoad = maxLoad;
    }

    @Override
    public String toString(){
        return String.format("node: workload attr: type:\"%s\" attr: minLoad=\"%d\" attr: maxLoad\"%d\"", type, minLoad, maxLoad);

    }
}
