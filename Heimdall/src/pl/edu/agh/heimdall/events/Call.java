package pl.edu.agh.heimdall.events;

public class Call extends Event {

    public final String type;
    public final String method;
    public final Object target;
    public final Object[] args;

    public Call(String thread, String type, String method, Object target,
            Object[] args) {
        super(thread);
        this.type = type;
        this.method = method;
        this.target = target;
        this.args = args;
    }

}
