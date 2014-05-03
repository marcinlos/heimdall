package rangarok;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MainWindow extends JFrame {

    public MainWindow() {
        super("Rangarok");
        init();
    }
    
    private void init() {
        Action action = new AbstractAction("unleash rangarok") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    react();
                } catch (Exception ee) {
                    // swallow, screw it
                }
            }
        };
        JButton button = new JButton(action);
        add(button);
    }
    
    private void react() {
        JOptionPane.showConfirmDialog(MainWindow.this, "Hello");
        for (int i = 0; i < 5; ++ i) {
            doSomeWork(i);
        }
    }
    
    private void doSomeWork(int n) {
        if (n > 3) {
            throw new RuntimeException("I don't wanna");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = new MainWindow();
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
                window.pack();
            }
        });
    }

}
