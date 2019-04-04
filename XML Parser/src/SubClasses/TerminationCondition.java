package SubClasses;

public class TerminationCondition {

    public String type;
    public int value;

    public TerminationCondition(String type, int value){
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("node: condition attr: type=\"%s\" attr: value=\"%d\"", type, value);
    }
}
