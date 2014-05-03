package pl.edu.agh.heimdall.events;

import pl.edu.agh.heimdall.consumer.EventConsumer;


public class Catch extends Event {

    public Catch(String thread) {
        super(thread);
    }

    @Override
    public void dispatch(EventConsumer consumer) {
        consumer.catched(this);
    }

}
