package pl.edu.agh.heimdall;

public privileged aspect Monitor {
    
    private pointcut internals(): within(pl.edu.agh.heimdall..*);
    
    pointcut monitored(): execution(* *(..)) || execution(*.new(..));
    
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
    
    before(Throwable e): handler(Throwable+) && args(e) {
        tracer().exceptionCatched(thisJoinPoint, e);
    }

}
