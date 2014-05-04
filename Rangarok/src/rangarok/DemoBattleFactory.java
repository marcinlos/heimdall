package rangarok;

import rangarok.entities.Creature;
import rangarok.entities.Entity;
import rangarok.mechanics.Battle;
import rangarok.mechanics.RegularBattle;
import rangarok.mechanics.Side;
import rangarok.mechanics.Stats;

public class DemoBattleFactory implements BattleFactory {

    @Override
    public Battle nextBattle(String name) {
        Battle battle = new RegularBattle(name);
        
        Stats stats = new Stats(1000, 100, 150);
        Entity odin = new Creature(0, "Odin", Side.GOOD, stats);
        battle.add(odin);
        
        stats = new Stats(1200, 90, 150);
        Entity fenrir = new Creature(1, "Fenrir", Side.EVIL, stats);
        battle.add(fenrir);
        
        return battle;
    }

}
