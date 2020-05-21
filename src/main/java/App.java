import game_managers.db.DBManager;
import game_managers.logicManagers.GameMainFrame;

import javax.swing.*;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater( new Runnable() {
            DBManager dbManager = DBManager.getInstance();

            @Override
            public void run() {
                GameMainFrame game = new GameMainFrame(dbManager);
            }
        } );
    }
}
