package pl.edu.agh.heimdall.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pl.edu.agh.heimdall.events.Event;


/**
 * Abstract base for classes whose purpose is to run in a background thread
 * and periodically consume events from {@link QueueEventSink}. Abstract method
 * {@link #consume(Event)} is ment for basic, one-by-one handling. If there
 * is a need to handle whole portion of events at once (e.g. to reduce
 * network communication by sending all events in one message), 
 * {@link #consume(List)} can be overriden.
 * 
 * @author los
 */
public abstract class QueueConsumer implements Runnable {

    private final QueueEventSink queue;
    private final List<Event> buffer;
    private int delay;
    
    public QueueConsumer(QueueEventSink queue, int bufferSize, int delay) {
        this.queue = queue;
        this.buffer = new ArrayList<>(bufferSize);
        this.delay = delay;
    }

    @Override
    public void run() {
        while (true) {
            queue.drain(buffer);
            consume(buffer);
            buffer.clear();
            delay();
        }
    }
    
    protected void consume(List<Event> events) {
        for (Event e : events) {
            consume(e);
        }
    }
    
    protected abstract void consume(Event e);
    
    private void delay() {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
}
