package rangarok;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import rangarok.server.GameServer;

public class Application {
    
    private final Executor worker = Executors.newSingleThreadExecutor();
    
    public void start() {
        GameServer server = new GameServer();
        worker.execute(server);
        createWindow(server);
    }

    private void createWindow(final GameServer server) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = new MainWindow(server);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
                window.pack();
            }
        });
    }
}
