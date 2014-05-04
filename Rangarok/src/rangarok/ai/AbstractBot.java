package rangarok.ai;

import rangarok.entities.Creature;
import rangarok.entities.Entity;
import rangarok.mechanics.Attack;
import rangarok.mechanics.Battle;
import rangarok.mechanics.MeleeAttack;
import rangarok.mechanics.Side;
import rangarok.mechanics.Stats;
import rangarok.server.Action;
import rangarok.server.FightAction;
import rangarok.server.GameServer;
import rangarok.server.Request;

public class AbstractBot implements Bot {

    private final GameServer server;
    protected final Battle battle;
    
    public AbstractBot(GameServer server, Battle battle) {
        this.server = server;
        this.battle = battle;
    }
    
    protected void execute(Action action) {
        String gameName = battle.getName();
        Request request = new Request(gameName, action);
        server.submit(request);
    }
    
    @Override
    public int tick() {
//        Stats stats = new Stats(1000, 100, 150);
//        Entity odin = new Creature(0, "Odin", Side.GOOD, stats);
//        
//        stats = new Stats(1200, 90, 150);
//        Entity fenrir = new Creature(1, "Fenrir", Side.EVIL, stats);
//
//        
//        Attack attack = new MeleeAttack();
//        Action action = new FightAction(odin, attack, fenrir);
//        execute(action);
        return 5000;
    }

    @Override
    public int init() {
        return 1000;
    }

}
