package rangarok.event;

import rangarok.entities.Entity;
import rangarok.mechanics.Attack;
import rangarok.mechanics.AttackResult;

public class EntityDamaged extends BattleEvent {
    
    private final Entity source;
    private final Entity target;
    private final Attack attack;
    private final AttackResult result;
    
    public EntityDamaged(Entity source, Entity target, Attack attack,
            AttackResult result) {
        this.source = source;
        this.target = target;
        this.attack = attack;
        this.result = result;
    }
    
    public Entity getSource() {
        return source;
    }

    public Entity getTarget() {
        return target;
    }

    public Attack getAttack() {
        return attack;
    }

    public AttackResult getResult() {
        return result;
    }

}
