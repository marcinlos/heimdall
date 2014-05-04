package rangarok.server;

import rangarok.entities.Entity;
import rangarok.mechanics.Attack;
import rangarok.mechanics.Battle;

public class RequestDispatcher  {

    private final Battle battle;
    
    public RequestDispatcher(Battle battle) {
        this.battle = battle;
    }

    public void handle(FightAction fight) {
        Entity offensor = fight.getOffensor();
        Attack attack = fight.getAttack();
        Entity target = fight.getTarget();
        
        battle.fight(offensor, attack, target);
    }
    
    public Battle getBattle() {
        return battle;
    }

}
