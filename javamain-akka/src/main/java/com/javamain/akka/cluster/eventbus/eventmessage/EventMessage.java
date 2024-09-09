package com.javamain.akka.cluster.eventbus.eventmessage;

public class EventMessage {
    private final String content;

    public EventMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
