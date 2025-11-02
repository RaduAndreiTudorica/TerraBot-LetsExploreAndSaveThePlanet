package main.output;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseOutput {
    private String command;
    private int timestamp;

    public BaseOutput(String name, int timestamp) {
        this.command = name;
        this.timestamp = timestamp;
    }

    public String getCommand() {
        return command;
    }

    public int getTimestamp() {
        return timestamp;
    }
}
