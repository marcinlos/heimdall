package pl.edu.agh.heimdall.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pl.edu.agh.heimdall.events.Event;

public abstract class QueueSinkConsumer extends QueueEventSink {

    private final List<Event> buffer;
    
    private final Executor executor = Executors.newSingleThreadExecutor();
    
    private int delay;
    
    
    public QueueSinkConsumer(int queueSize, int bufferSize, int delay) {
        super(queueSize);
        
        this.buffer = new ArrayList<>(100);
        this.delay = delay;
        
        executor.execute(new Runnable() {
            @Override
            public void run() {
                consumerLoop();
            }
        });
    }

    
    private void consumerLoop() {
        while (true) {
            drain(buffer);
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
