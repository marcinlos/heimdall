package rangarok.entities;

import rangarok.mechanics.Side;

public class BaseEntity implements Entity {

    private final int id;
    private final String name;
    private Side side;

    public BaseEntity(int id, String name, Side side) {
        this.id = id;
        this.name = name;
        this.side = side;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Side getSide() {
        return side;
    }

}
