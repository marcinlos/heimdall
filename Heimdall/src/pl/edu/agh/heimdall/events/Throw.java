package pl.edu.agh.heimdall.events;

public class Throw extends Event {

    public final Throwable exception;

    public Throw(String thread, Throwable exception) {
        super(thread);
        this.exception = exception;
    }

}
