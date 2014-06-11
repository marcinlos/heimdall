package rangarok;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pl.edu.agh.heimdall.annotations.ImpactOn;
import pl.edu.agh.heimdall.annotations.ListenerTrace;
import pl.edu.agh.heimdall.annotations.Marked;

import com.google.common.eventbus.Subscribe;

import rangarok.entities.Creature;
import rangarok.entities.Entity;
import rangarok.event.EntityDamaged;
import rangarok.mechanics.Attack;
import rangarok.mechanics.Battle;
import rangarok.mechanics.MeleeAttack;
import rangarok.mechanics.Side;
import rangarok.mechanics.Stats;
import rangarok.server.Action;
import rangarok.server.FightAction;
import rangarok.server.GameServer;
import rangarok.server.Request;
import pl.edu.agh.heimdall.annotations.*;

@ImpactOn
public class BattleWindow extends JPanel {

    private final Random rand = new Random();
    private final GameServer server;
    
    private final Battle battle;
    
    private final JLabel testLabel = new JLabel("TEST");
 
    private final javax.swing.Action doSth = new AbstractAction("Do something") {
        @Override
        
        public void actionPerformed(ActionEvent e) {
            doSomething();
        }
    };
    
    @ListenerTrace
	private JButton button;
    
    public BattleWindow(GameServer server, Battle battle) {
        this.server = server;
        this.battle = battle;
        
        battle.getEventBus().register(this);
        add(testLabel);
        
        button = new JButton(doSth);
        
        add(new JButton(new AbstractAction("test") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
chngText();				
			}
		}));
        
        add(button);
    }
    
    private void chngText(){
        testLabel.setText("uuup");
    }
    
    private void execute(Action action) {
        String name = battle.getName();
        Request request = new Request(name, action);
        server.submit(request);
    }
    
    @Subscribe
    public void whenEntityDamaged(final EntityDamaged entity) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                float hue = rand.nextFloat();
                setBackground(Color.getHSBColor(hue, 1, 1));
            }
        });
    }
    
    private void doSomething() {
        Stats stats = new Stats(1000, 100, 150);
        Entity odin = new Creature(0, "Odin", Side.GOOD, stats);
        
        stats = new Stats(1200, 90, 150);
        Entity fenrir = new Creature(1, "Fenrir", Side.EVIL, stats);

        
        Attack attack = new MeleeAttack();
        Action action = new FightAction(odin, attack, fenrir);
        execute(action);
    }
}
