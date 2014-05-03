package pl.edu.agh.heimdall.consumer;

import pl.edu.agh.heimdall.Tracer;
import pl.edu.agh.heimdall.events.Event;

/**
 * Low-level interface for receiving tracing data. Used directly by the
 * {@link Tracer} and certaing other sink implementations. For logical handling
 * use {@link EventConsumer}.
 * 
 * @author los
 */
public interface EventSink {

    /**
     * Forward the event to the sink
     */
    void push(Event e);

}
