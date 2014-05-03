package pl.edu.agh.heimdall.printer;

import pl.edu.agh.heimdall.consumer.EventConsumer;
import pl.edu.agh.heimdall.consumer.EventSink;
import pl.edu.agh.heimdall.events.Call;
import pl.edu.agh.heimdall.events.Catch;
import pl.edu.agh.heimdall.events.Event;
import pl.edu.agh.heimdall.events.Return;
import pl.edu.agh.heimdall.events.Throw;

class StdoutPrinter implements EventConsumer, EventSink {
    
    private int depth = 0;
    private boolean stackUnwinding = false;
    private final String thread;

    public StdoutPrinter(String thread) {
        this.thread = thread;
    }
    
    @Override
    public void called(Call callInfo) {
        out("Call: %s", callInfo.method);
        ++depth;
    }

    @Override
    public void returned(Return returnInfo) {
        --depth;
        out("returned %s", returnInfo.value);
    }

    @Override
    public void throwed(Throw throwInfo) {
        --depth;
        if (! stackUnwinding) {
            stackUnwinding = true;
            out("Exception: " + throwInfo.exception.getMessage());
        } else {
            out("<unwinding...>");
        }
    }

    @Override
    public void catched(Catch catchInfo) {
        out("Exception catched");
    }
    
    private void out(String msg, Object... params) {
        String tid = "[" + thread + "] ";
        String fullMessage = String.format(msg, params);
        System.out.println(tid + indent(depth) + fullMessage);
    }

    private static String indent(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ++ i) {
            sb.append("  ");
        }
        return sb.toString();
    }

    @Override
    public void push(Event e) {
        e.dispatch(this);
    }

}
