package rangarok.mechanics;

import com.google.common.eventbus.EventBus;

import rangarok.entities.Entity;

public interface Battle {

    void fight(Entity offensor, Attack attack, Entity target);
    
    void add(Entity entity);
    
    EventBus getEventBus();
    
    String getName();
    
}
