package pl.edu.agh.heimdall.events;

import java.io.Serializable;

public class Event implements Serializable {

    public final String thread;
    
    public Event(String thread) {
        this.thread = thread;
    }
    
}
