package rangarok.mechanics;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import rangarok.entities.Entity;

public class BattleState {
    
    private final Deque<Effect> globalEffects = new ArrayDeque<>();
    private final Set<Entity> participants = new HashSet<>();
    
    
    public void add(Entity entity) {
        participants.add(entity);
    }
    
    public void remove(Entity entity) {
        participants.remove(entity);
    }
    
    public Collection<Entity> getEntities(Side side) {
        Set<Entity> guys = new HashSet<>();
        for (Entity entity : participants) {
            if (entity.getSide() == side) {
                guys.add(entity);
            }
        }
        return guys;
    }
    
    public Collection<Entity> getEntities() {
        return Collections.unmodifiableCollection(participants);
    }
    
    public Collection<Effect> getEffects() {
        return Collections.unmodifiableCollection(globalEffects);
    }
    
    public void add(Effect effect) {
        globalEffects.add(effect);
    }
    
    public void remove(Effect effect) {
        globalEffects.remove(effect);
    }

}
