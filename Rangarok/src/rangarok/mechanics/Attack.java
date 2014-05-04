package rangarok.mechanics;

import java.util.Collection;

import rangarok.entities.Entity;

public interface Attack {
    
    int getDamage(Entity offensor, Entity target);
    
    Collection<Effect> getCausedEffects(Entity offensor, Entity target);

}
