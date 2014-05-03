package pl.edu.agh.heimdall;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

public class Tracer {

    private int depth = 0;
    private boolean stackUnwinding = false;
    
    private final String thread;
    
    public Tracer() {
        this.thread = Thread.currentThread().getName();
    }
    
    private static String indent(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ++ i) {
            sb.append("  ");
        }
        return sb.toString();
    }
    
    private void out(String msg, Object... params) {
        String tid = "[" + thread + "] ";
        String fullMessage = String.format(msg, params);
        System.out.println(tid + indent(depth) + fullMessage);
    }

    public void beginCall(JoinPoint point) {
        Signature signature = point.getSignature();
        String method = signature.toLongString();
        out("Call: %s", method);
        ++depth;
    }

    public void callReturns(Object value) {
        --depth;
        out("returned %s", value);
    }
    
    public void callThrows(Throwable e) {
        --depth;
        if (! stackUnwinding) {
            stackUnwinding = true;
            out("Exception: " + e.getMessage());
        } else {
            out("<unwinding...>");
        }
    }
    
    public void exceptionCatched(JoinPoint point, Throwable e) {
        out("Exception catched");
    }

}
