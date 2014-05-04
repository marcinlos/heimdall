package rangarok.mechanics;

import rangarok.entities.Entity;

public interface BattleEngine {
    
    AttackResult attack(Entity offensor, Attack attack, Entity target);
    
}
