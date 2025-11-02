package main.output;

import java.util.List;

public class KnowledgeBaseOutput {
    private String topic;
    private List<String> facts;

    public KnowledgeBaseOutput(String topic, List<String> facts) {
        this.topic = topic;
        this.facts = facts;
    }

    public String getTopic() { return topic; }
    public List<String> getFacts() { return facts; }
}
