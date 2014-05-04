package rangarok.mechanics;

import rangarok.entities.Entity;

public interface Effect {
    
    void onAttack(Entity entity, Stats stats);
    
    void onHit(Entity entity, Stats stats);

}
