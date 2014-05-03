package pl.edu.agh.heimdall;

public privileged aspect Monitor {
    
    pointcut monitored(): call(* *(..)) && ! within(Monitor);
    
    Object around() : monitored() {
        System.out.println(thisJoinPoint);
        return proceed();
    }

}
