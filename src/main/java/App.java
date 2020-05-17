import game_managers.logicManagers.GameMainFrame;

import javax.swing.*;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                GameMainFrame game = new GameMainFrame();
            }
        } );
    }
}
