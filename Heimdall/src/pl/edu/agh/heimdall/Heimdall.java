package pl.edu.agh.heimdall;

import pl.edu.agh.heimdall.consumer.EventSink;
import pl.edu.agh.heimdall.printer.PrinterConsumer;

public class Heimdall {
    
    static {
        consumer = new PrinterConsumer(1000, 100, 100);
    }
    
    private static final EventSink consumer;

    private static final ThreadLocal<Tracer> tracers = new ThreadLocal<Tracer>() {
        @Override
        protected Tracer initialValue() {
            return new Tracer(consumer);
        }
    };

    public static Tracer getTracer() {
        return tracers.get();
    }

}
