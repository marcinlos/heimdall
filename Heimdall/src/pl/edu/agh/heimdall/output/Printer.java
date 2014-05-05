package pl.edu.agh.heimdall.output;

import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.heimdall.consumer.EventSink;
import pl.edu.agh.heimdall.consumer.QueueConsumer;
import pl.edu.agh.heimdall.consumer.QueueEventSink;
import pl.edu.agh.heimdall.events.Event;

public class Printer extends QueueConsumer {
    
    private final Map<String, EventSink> sinks = new HashMap<>();
    
    public Printer(QueueEventSink queue, int bufferSize, int delay) {
        super(queue, bufferSize, delay);
    }
    
    private EventSink getSink(String thread) {
        EventSink sink = sinks.get(thread);
        if (sink == null) {
            sink = new StdoutPrinter(thread);
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
