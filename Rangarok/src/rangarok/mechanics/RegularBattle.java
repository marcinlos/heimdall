package rangarok.mechanics;

import com.google.common.eventbus.EventBus;

import rangarok.entities.Entity;
import rangarok.event.EntityDamaged;
import rangarok.event.BattleEvent;
import rangarok.event.NewEntity;

public class RegularBattle implements Battle {
    
    private final String name;
    private final BattleState state = new BattleState();
    private final BattleEngine engine;
    private final EventBus eventBus = new EventBus();
    
    public RegularBattle(String name) {
        this.name = name;
        this.engine = new SimpleBattleEngine(state);
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }
    
    @Override
    public void fight(Entity offensor, Attack attack, Entity target) {
        AttackResult result = engine.attack(offensor, attack, target);
        
        BattleEvent event = new EntityDamaged(offensor, target, attack, result);
        eventBus.post(event);
    }

    @Override
    public void add(Entity entity) {
        state.add(entity);
        BattleEvent event = new NewEntity(entity);
        eventBus.post(event);
    }

    @Override
    public String getName() {
        return name;
    }

}
