package rangarok.entities;

import java.util.ArrayList;
import java.util.List;

import rangarok.mechanics.Effect;
import rangarok.mechanics.Side;
import rangarok.mechanics.Stats;

public class Creature extends BaseEntity {
    
    private int hp;
    protected final Stats stats;
    
    protected final List<Effect> effects = new ArrayList<>();

    
    public Creature(int id, String name, Side side, Stats stats) {
        super(id, name, side);
        this.stats = stats;
    }
    
    public void add(Effect effect) {
        effects.add(effect);
    }

    boolean changeHp(int amount) {
        hp += amount;
        adjustHp();
        return isAlive();
    }
    
    private void adjustHp() {
        if (hp > stats.getMaxHp()) {
            hp = stats.getMaxHp();
        } else if (hp < 0) {
            hp = 0;
        }
    }
    
    public Stats attackStats(Entity enemy) {
        Stats s = stats.clone();
        
        for (Effect effect : effects) {
            effect.onAttack(enemy, s);
        }
        return s;
    }
    
    public Stats hitStats(Entity enemy) {
        Stats s = stats.clone();
        
        for (Effect effect : effects) {
            effect.onHit(enemy, s);
        }
        return s;
    }
    
    public boolean isAlive() {
        return hp > 0;
    }

    public Stats getStats() {
        return stats;
    }

}
