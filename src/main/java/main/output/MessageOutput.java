package main.output;

public class MessageOutput extends BaseOutput{
    private String message;

    public MessageOutput(String command, int timestamp, String message){
        super(command, timestamp);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
