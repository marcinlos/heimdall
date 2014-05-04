package rangarok.event;

import rangarok.entities.Entity;

public class NewEntity extends BattleEvent {

    private final Entity entity;
    
    public NewEntity(Entity entity) {
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return entity;
    }

}
