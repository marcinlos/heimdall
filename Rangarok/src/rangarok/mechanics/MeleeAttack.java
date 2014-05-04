package rangarok.mechanics;

import java.util.Collection;
import java.util.Collections;

import rangarok.entities.Creature;
import rangarok.entities.Entity;

public class MeleeAttack implements Attack {

    @Override
    public int getDamage(Entity offensor, Entity target) {
        if (offensor instanceof Creature) {
            Creature creature = (Creature) offensor;
            return creature.getStats().getDmg();
        }
        return 0;
    }

    @Override
    public Collection<Effect> getCausedEffects(Entity offensor, Entity target) {
        return Collections.emptyList();
    }

}
