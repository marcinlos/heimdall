package rangarok.mechanics;

import rangarok.entities.Entity;

public class SimpleBattleEngine implements BattleEngine {
    
    private final BattleState state;
    
    public SimpleBattleEngine(BattleState state) {
        this.state = state;
    }
    
    @Override
    public AttackResult attack(Entity offensor, Attack attack, Entity target) {
        // TODO: some rules that call stuff :P
        return new AttackResult(Outcome.DAMAGED, 10);
    }

}
