package rangarok;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import rangarok.ai.AbstractBot;
import rangarok.ai.Bot;
import rangarok.mechanics.Battle;
import rangarok.server.GameServer;


public class MainWindow extends JFrame {
    
    private final GameServer server;
    private JTabbedPane battleTabs;
    private JMenuBar menuBar;
    private final BattleFactory factory = new DemoBattleFactory();
    

    private final Action newRangarok = new AbstractAction("New Rangarok") {
        @Override
        public void actionPerformed(ActionEvent e) {
            createNewRangarok();
        }
    };
    
    public MainWindow(GameServer server) {
        super("Rangarok");
        this.server = server;
        
        initGUI();
    }

    private void initGUI() {
        setPreferredSize(new Dimension(800, 500));
        
        battleTabs = new JTabbedPane();
        
        menuBar = new JMenuBar();
        
        JMenu mainMenu = new JMenu("Rangarok");
        mainMenu.add(newRangarok);
        
        menuBar.add(mainMenu);
        
        setJMenuBar(menuBar);
        add(battleTabs);
    }
    
    private void createNewRangarok() {
        Battle battle = factory.nextBattle("Demo");
        server.addGame(battle);
        Bot bot = new AbstractBot(server, battle);
        server.addBot(bot);
        
        BattleWindow tab = new BattleWindow(server, battle);
        battleTabs.add("Battle", tab);
    }
    
}
