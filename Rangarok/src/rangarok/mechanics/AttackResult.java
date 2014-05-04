package rangarok.mechanics;

public class AttackResult {
    
    private final Outcome outcome;
    private final int value;

    public AttackResult(Outcome outcome, int value) {
        this.outcome = outcome;
        this.value = value;
        throw new RuntimeException();
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public int getValue() {
        return value;
    }

}
