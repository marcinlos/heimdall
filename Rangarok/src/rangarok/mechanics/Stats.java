package rangarok.mechanics;

public class Stats implements Cloneable {
    
    private int maxHp;
    private int dmg;
    private int def;

    public Stats(int maxHp, int dmg, int def) {
        this.maxHp = maxHp;
        this.dmg = dmg;
        this.def = def;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }
    
    public Stats clone() {
        return new Stats(maxHp, dmg, def);
    }

}
