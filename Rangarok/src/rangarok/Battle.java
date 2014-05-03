package rangarok;

public class Battle {

    public void fight() {
        duel("Odin", "Fenrir");
        duel("Thor", "Jörmungandr");
    }
    
    void duel(String a, String b) {
        System.out.println(a + " vs " + b);
    }

}
