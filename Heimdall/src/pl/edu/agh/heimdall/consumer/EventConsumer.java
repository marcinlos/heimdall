package pl.edu.agh.heimdall.consumer;

import pl.edu.agh.heimdall.events.Call;
import pl.edu.agh.heimdall.events.Catch;
import pl.edu.agh.heimdall.events.Return;
import pl.edu.agh.heimdall.events.Throw;

public interface EventConsumer {
    
    void called(Call callInfo);
    
    void returned(Return returnInfo);
    
    void throwed(Throw throwInfo);
    
    void catched(Catch catchInfo);

}
