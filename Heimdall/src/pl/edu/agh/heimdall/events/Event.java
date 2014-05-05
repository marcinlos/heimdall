package pl.edu.agh.heimdall.events;

import java.io.Serializable;
import java.util.Date;

import pl.edu.agh.heimdall.consumer.EventConsumer;


public abstract class Event implements Serializable {

    public final String thread;
    public final long timestamp;
    
    public Event(String thread) {
        this.thread = thread;
        this.timestamp = new Date().getTime();
    }
    
    public abstract void dispatch(EventConsumer consumer);
    
}
