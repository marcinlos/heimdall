package pl.edu.agh.heimdall;

public abstract privileged aspect Monitor {
    
    private pointcut internals(): within(pl.edu.agh.heimdall..*);
    
    abstract pointcut monitored();
    
    abstract pointcut tracedCatch();
    
    private pointcut affected(): monitored() && !internals();
    
    private Tracer tracer() {
        return Heimdall.getTracer();
    }
    
    Object around() : affected() {
        tracer().beginCall(thisJoinPoint);
        Object value = proceed();
        tracer().callReturns(value);
        return value;
    }
    
    after() throwing (Exception e): affected() {
        tracer().callThrows(e);
    }
    
    before(Throwable e): tracedCatch() && handler(Throwable+) && args(e) {
        tracer().exceptionCatched(thisJoinPoint, e);
    }

}
