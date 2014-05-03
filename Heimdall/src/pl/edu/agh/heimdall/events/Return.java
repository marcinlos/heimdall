package pl.edu.agh.heimdall.events;

import pl.edu.agh.heimdall.consumer.EventConsumer;


public class Return extends Event {
    
    public final Object value;

    public Return(String thread, Object value) {
        super(thread);
        this.value = value;
    }

    @Override
    public void dispatch(EventConsumer consumer) {
        consumer.returned(this);
    }

}
