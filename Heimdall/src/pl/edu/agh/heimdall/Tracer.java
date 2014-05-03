package pl.edu.agh.heimdall;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import pl.edu.agh.heimdall.consumer.EventSink;
import pl.edu.agh.heimdall.events.Call;
import pl.edu.agh.heimdall.events.Catch;
import pl.edu.agh.heimdall.events.Event;
import pl.edu.agh.heimdall.events.Return;
import pl.edu.agh.heimdall.events.Throw;

public class Tracer {

    private final String thread;
    private final EventSink sink;
    
    public Tracer(EventSink sink) {
        this.sink = sink;
        this.thread = Thread.currentThread().getName();
    }
    
    public void beginCall(JoinPoint point) {
        Signature signature = point.getSignature();
        
        String method = signature.toString();
        Object target = point.getTarget();
        String type = signature.getDeclaringTypeName();
        
        Event event = new Call(thread, type, method, target, point.getArgs());
        sink.push(event);
    }

    public void callReturns(Object value) {
        Event event = new Return(thread, value);
        sink.push(event);
    }
    
    public void callThrows(Throwable e) {
        Event event = new Throw(thread, e);
        sink.push(event);
    }
    
    public void exceptionCatched(JoinPoint point, Throwable e) {
        Event event = new Catch(thread);
        sink.push(event);
    }

}
