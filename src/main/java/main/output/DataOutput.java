package main.output;

public class DataOutput extends BaseOutput{
    private Object output;

    public DataOutput(String command, int timestamp, Object output){
        super(command, timestamp);
        this.output = output;
    }

    public Object getOutput(){
        return output;
    }
}
