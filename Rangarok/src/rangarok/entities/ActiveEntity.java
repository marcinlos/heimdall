package rangarok.entities;

import rangarok.mechanics.BattleState;

public interface ActiveEntity extends Entity {
    
    void act(BattleState battlefield);

}
