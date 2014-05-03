package pl.edu.agh.heimdall;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import pl.edu.agh.heimdall.consumer.EventSink;
import pl.edu.agh.heimdall.events.Call;
import pl.edu.agh.heimdall.events.Catch;
import pl.edu.agh.heimdall.events.Event;
import pl.edu.agh.heimdall.events.Return;
import pl.edu.agh.heimdall.events.Throw;

/**
 * Class directly used by the tracing aspect. Receives information about all
 * the traced events. It is stateless and doesn't process received data in
 * any significant way, just forwards it to the {@link EventSink}. The intent
 * is to keep the tracing lightweight by decoupling reception and processing.
 * Depending on sink configuration, processing may take place immediately
 * anyway, in background thread, or even in other process. 
 * 
 * @author los
 */
public class Tracer {

    /**
     * Where to forward tracing data to
     */
    private final EventSink sink;
    
    public Tracer(EventSink sink) {
        this.sink = sink;
    }
    
    private String thread() {
        return Thread.currentThread().getName();
    }
    
    public void beginCall(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        
        String method = signature.toString();
        Object target = joinPoint.getTarget();
        String type = signature.getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();
        
        Event event = new Call(thread(), type, method, target, args);
        sink.push(event);
    }

    public void callReturns(Object value) {
        Event event = new Return(thread(), value);
        sink.push(event);
    }
    
    public void callThrows(Throwable e) {
        Event event = new Throw(thread(), e);
        sink.push(event);
    }
    
    public void exceptionCatched(JoinPoint joinPoint, Throwable e) {
        Event event = new Catch(thread());
        sink.push(event);
    }

}
