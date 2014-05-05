package pl.edu.agh.heimdall.output;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pl.edu.agh.heimdall.consumer.EventConsumer;
import pl.edu.agh.heimdall.consumer.QueueConsumer;
import pl.edu.agh.heimdall.consumer.QueueEventSink;
import pl.edu.agh.heimdall.events.Call;
import pl.edu.agh.heimdall.events.Catch;
import pl.edu.agh.heimdall.events.Event;
import pl.edu.agh.heimdall.events.Return;
import pl.edu.agh.heimdall.events.Throw;

public class SocketOutput extends QueueConsumer {

    private final Socket socket;
    private final ObjectOutput out;
    
    private EventConsumer flattener = new EventConsumer() {
        
        @Override
        public void throwed(Throw throwInfo) {
            doConsume(throwInfo);
        }
        
        @Override
        public void returned(Return returnInfo) {
            doConsume(new Return(returnInfo.thread, String.valueOf(returnInfo.value)));
        }
        
        @Override
        public void catched(Catch catchInfo) {
            doConsume(catchInfo);
        }
        
        @Override
        public void called(Call callInfo) {
            Object[] args = new Object[callInfo.args.length];
            for (int i = 0; i < callInfo.args.length; ++ i) {
                args[i] = String.valueOf(callInfo.args[i]);
            }
            Object target = String.valueOf(callInfo.target);
            
            Call info = new Call(callInfo.thread, callInfo.type, callInfo.method, target, args);
            doConsume(info);
        }
        
        private void doConsume(Event e) {
            try {
                out.writeObject(e);
            } catch (IOException e1) {
                e1.printStackTrace(System.err);
            }
        }
    };

    public SocketOutput(QueueEventSink queue, int bufferSize, int delay,
            String host, int port) {
        super(queue, bufferSize, delay);
        try {
            this.socket = new Socket(host, port);
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void consume(Event e) {
        e.dispatch(flattener);
    }

}
