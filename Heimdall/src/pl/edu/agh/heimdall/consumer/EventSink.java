package pl.edu.agh.heimdall.consumer;

import pl.edu.agh.heimdall.events.Event;

public interface EventSink {
    
    void push(Event e);

}
