import game_managers.db.DBManager;
import game_managers.logicManagers.GameMainFrame;
import utilities.GameLogger;

import javax.swing.*;

/**
 * @author silviu hartan
 * @version  1.0
 * @since 2020-05-15
 * Main starting point
 * invokes the swing thread
 */

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater( new Runnable() {
            DBManager dbManager = DBManager.getInstance();

            @Override
            public void run() {
                GameMainFrame game = new GameMainFrame( dbManager );
            }
        } );
    }
}
