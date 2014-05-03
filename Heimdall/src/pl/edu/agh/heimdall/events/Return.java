package pl.edu.agh.heimdall.events;

public class Return extends Event {
    
    public final Object value;

    public Return(String thread, Object value) {
        super(thread);
        this.value = value;
    }

}
