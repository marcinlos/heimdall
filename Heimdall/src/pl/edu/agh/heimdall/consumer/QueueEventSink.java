package pl.edu.agh.heimdall.consumer;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import pl.edu.agh.heimdall.events.Event;


/**
 * {@link EventSink} implementations based on concurrent queue. Useful for
 * decoupling tracing event reception and handling, as it allows to process
 * it using producer-consumer pattern. Stored data needs to be fetched
 * relatively frequently, as in the case of using up all the space next entries
 * will be discarded. Alternative would be to block until some entries are 
 * removed, but this might be an undesirable as it strongly affects the traced
 * application.
 * 
 * @author los
 */
public class QueueEventSink implements EventSink {
    
    private final BlockingQueue<Event> queue;

    
    public QueueEventSink(int queueSize) {
        this.queue = new ArrayBlockingQueue<>(queueSize);
    }

    @Override
    public void push(Event e) {
        if (!queue.offer(e)) {
            System.out.println("Missed event");
        }
    }
    
    public void drain(Collection<? super Event> to) {
        queue.drainTo(to);
    }
    
}
