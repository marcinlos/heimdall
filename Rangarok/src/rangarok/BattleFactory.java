package rangarok;

import rangarok.mechanics.Battle;

public interface BattleFactory {
    
    Battle nextBattle(String name);
    
}
