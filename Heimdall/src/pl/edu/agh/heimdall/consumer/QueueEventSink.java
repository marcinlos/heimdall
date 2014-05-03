package pl.edu.agh.heimdall.consumer;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import pl.edu.agh.heimdall.events.Event;


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
    
    protected void drain(Collection<? super Event> to) {
        queue.drainTo(to);
    }
    
}
