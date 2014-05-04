package rangarok.server;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import rangarok.ai.Bot;
import rangarok.ai.BotEngine;
import rangarok.mechanics.Battle;

public class GameServer implements Runnable {
    
    private static final int QUEUE_SIZE = 10;
    
    private final BlockingQueue<Request> requests;
    private final Map<String, RequestDispatcher> dispatchers;
    
    private final BotEngine botEngine = new BotEngine();
    
    {
        requests = new ArrayBlockingQueue<>(QUEUE_SIZE);
        dispatchers = new ConcurrentHashMap<>();
    }

    public void addGame(Battle battle) {
        RequestDispatcher dispatcher = new RequestDispatcher(battle);
        String name = battle.getName();
        dispatchers.put(name, dispatcher);
    }
    
    public void addGameListener(int gameId, Object listener) {
        RequestDispatcher dispatcher = dispatchers.get(gameId);
        if (dispatcher != null) {
            Battle battle = dispatcher.getBattle();
            battle.getEventBus().register(listener);
        }
    }
    
    public void addBot(Bot bot) {
        botEngine.add(bot);
    }
    
    public void submit(Request request) {
        try {
            requests.put(request);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted");
        }
    }

    @Override
    public void run() {
        while (true) {
            pollQueue();
        }
    }

    private Request takeOne() {
        while (true) {
            try {
                return requests.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupted");
            }
        }
    }
    
    private void pollQueue() {
        try {
            Request next = takeOne();
            handleOne(next);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    
    private void handleOne(Request request) {
        String name = request.getGameName();
        RequestDispatcher dispatcher = dispatchers.get(name);
        Action action = request.getAction();
        action.dispatch(dispatcher);
    }

}
