package pl.edu.agh.heimdall;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pl.edu.agh.heimdall.consumer.EventSink;
import pl.edu.agh.heimdall.consumer.QueueEventSink;
import pl.edu.agh.heimdall.output.Printer;
import pl.edu.agh.heimdall.output.SocketOutput;

/**
 * Entry point, handles configuration management and creates the main 
 * {@link EventSink} and {@link Tracer} object.
 * 
 * @author los
 */
public class Heimdall {
    
    private static final EventSink sink;
    private static final Tracer tracer;
    
    private static final Executor executor = Executors.newSingleThreadExecutor();
    
    static {
        QueueEventSink queue = new QueueEventSink(1000);
        
        sink = queue;
        tracer = new Tracer(sink);
        
//        executor.execute(new Printer(queue, 100, 100));
        executor.execute(new SocketOutput(queue, 100, 100, "localhost", 2000));
    }

    public static Tracer getTracer() {
        return tracer;
    }

}
