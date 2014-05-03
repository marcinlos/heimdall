package pl.edu.agh.heimdall.events;

import java.io.Serializable;

import pl.edu.agh.heimdall.consumer.EventConsumer;


public abstract class Event implements Serializable {

    public final String thread;
    
    public Event(String thread) {
        this.thread = thread;
    }
    
    public abstract void dispatch(EventConsumer consumer);
    
}
