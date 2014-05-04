package rangarok.server;

public class Request {
    
    private final String gameName;
    private final Action action;

    public Request(String gameName, Action action) {
        this.gameName = gameName;
        this.action = action;
    }
    
    public String getGameName() {
        return gameName;
    }
    
    public Action getAction() {
        return action;
    }

}
