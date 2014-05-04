package rangarok.entities;

import rangarok.mechanics.BattleState;
import rangarok.mechanics.Side;
import rangarok.mechanics.Stats;

public class ActiveCreature extends Creature implements ActiveEntity {

    public ActiveCreature(int id, String name, Side side, Stats stats) {
        super(id, name, side, stats);
    }

    @Override
    public void act(BattleState battlefield) {
        // do nothing
    }

}
