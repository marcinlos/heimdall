package rangarok.server;

import rangarok.entities.Entity;
import rangarok.mechanics.Attack;

public class FightAction extends Action {
    
    private final Entity offensor;
    private final Attack attack;
    private final Entity target;


    public FightAction(Entity offensor, Attack attack, Entity target) {
        this.offensor = offensor;
        this.attack = attack;
        this.target = target;
    }

    public Entity getOffensor() {
        return offensor;
    }

    public Attack getAttack() {
        return attack;
    }

    public Entity getTarget() {
        return target;
    }

    @Override
    protected void dispatch(RequestDispatcher dispatcher) {
        dispatcher.handle(this);
    }

}
