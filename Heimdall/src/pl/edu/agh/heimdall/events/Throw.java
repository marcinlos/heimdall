package pl.edu.agh.heimdall.events;

import pl.edu.agh.heimdall.consumer.EventConsumer;


public class Throw extends Event {

    public final Throwable exception;

    public Throw(String thread, Throwable exception) {
        super(thread);
        this.exception = exception;
    }

    @Override
    public void dispatch(EventConsumer consumer) {
        consumer.throwed(this);
    }

}
