package pl.edu.agh.heimdall.printer;

import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.heimdall.consumer.DispatcherEventSink;
import pl.edu.agh.heimdall.consumer.EventConsumer;
import pl.edu.agh.heimdall.consumer.EventSink;
import pl.edu.agh.heimdall.consumer.QueueSinkConsumer;
import pl.edu.agh.heimdall.events.Event;

public class PrinterConsumer extends QueueSinkConsumer {
    
    private final Map<String, EventSink> sinks = new HashMap<>();
    
    
    public PrinterConsumer(int queueSize, int bufferSize, int delay) {
        super(queueSize, bufferSize, delay);
    }
    
    public EventSink getSink(String thread) {
        EventSink sink = sinks.get(thread);
        if (sink == null) {
            EventConsumer consumer = new ThreadEventPrinter(thread);
            sink = new DispatcherEventSink(consumer);
            sinks.put(thread, sink);
        }
        return sink;
    }

    @Override
    protected void consume(Event e) {
        EventSink sink = getSink(e.thread);
        sink.push(e);
    }

}
