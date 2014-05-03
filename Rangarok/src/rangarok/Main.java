package rangarok;

public class Main {

    public static void main(String[] args) {
        Battle battle = new Battle();
        battle.fight();
        throw new RuntimeException();
    }

}
