import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class serverManager implements Runnable{

    public boolean run = true;

    @Override
    public void run() {
        while (run) {
            ConcurrentHashMap<String, Integer> cue = new ConcurrentHashMap<>();
            for(Server s : jobScheduler.Servers.getServers()){
                if(cue.contains(s.type)){
                    int total = cue.get(s.type) + (s.coreCount - s.flagCores);
                    cue.remove(s.type);
                    cue.put(s.type, total);
                } else {
                    cue.put(s.type, (s.coreCount - s.flagCores));
                }
            }

            for (Server s : jobScheduler.Servers.getServers()) {
                Iterator<serverFlag> sf = s.jobs.iterator();
                while (sf.hasNext()) {
                    serverFlag flag = sf.next();
                    if (flag.time <= System.currentTimeMillis()) {
                        //System.out.println(s.type + " flag: " + flag.flag + " time: " + (flag.time - System.currentTimeMillis()));
                        flag.doFlag(s);
                        sf.remove();
                    }
                }
            }
        }
    }
}

