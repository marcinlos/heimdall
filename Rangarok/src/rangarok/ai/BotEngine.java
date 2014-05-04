package rangarok.ai;

import java.util.Timer;
import java.util.TimerTask;

public class BotEngine {

    private static final int DELAY_AT_ERROR = 100;
    
    private final Timer timer = new Timer("ai-thread");
    

    public void add(Bot bot) {
        TimerTask initTask = botInitTask(bot);
        timer.schedule(initTask, 0);
    }

    private TimerTask botInitTask(final Bot bot) {
        return new TimerTask() {
            @Override
            public void run() {
                int delay = bot.init();
                if (delay >= 0) {
                    TimerTask tick = botTickTask(bot);
                    timer.schedule(tick, delay);
                }
            }
        };
    }
    
    private TimerTask botTickTask(final Bot bot) {
        return new TimerTask() {
            @Override
            public void run() {
                int delay = executeTick(bot);
                if (delay >= 0) {
                    TimerTask next = botTickTask(bot);
                    timer.schedule(next, delay);
                }
            }
        };
    }
    
    private int executeTick(Bot bot) {
        try {
            return bot.tick();
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
            return DELAY_AT_ERROR;
        }
    }

}
