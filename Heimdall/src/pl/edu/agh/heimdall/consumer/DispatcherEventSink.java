package pl.edu.agh.heimdall.consumer;

import pl.edu.agh.heimdall.events.Call;
import pl.edu.agh.heimdall.events.Catch;
import pl.edu.agh.heimdall.events.Event;
import pl.edu.agh.heimdall.events.Return;
import pl.edu.agh.heimdall.events.Throw;

public class DispatcherEventSink implements EventSink {

    private final EventConsumer consumer;
    
    public DispatcherEventSink(EventConsumer consumer) {
        this.consumer = consumer;
    }
    
    @Override
    public void push(Event e) {
        if (e instanceof Call) {
            consumer.called((Call) e);
        } else if (e instanceof Return) {
            consumer.returned((Return) e);
        } else if (e instanceof Throw) {
            consumer.throwed((Throw) e);
        } else if (e instanceof Catch) {
            consumer.catched((Catch) e);
        }
    }

}
