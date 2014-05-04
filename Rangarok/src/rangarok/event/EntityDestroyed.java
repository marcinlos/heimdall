package rangarok.event;

import rangarok.entities.Entity;

public class EntityDestroyed extends BattleEvent {
    
    private final Entity entity;

    public EntityDestroyed(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

}
