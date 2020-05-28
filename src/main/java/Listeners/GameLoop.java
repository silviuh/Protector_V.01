package Listeners;

import Constants.Constants;
import game_managers.logicManagers.GamePanel;
import game_managers.logicManagers.GameStateManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Observer used to perform a game loop: update + render
 */
public class GameLoop implements ActionListener {

    private GamePanel        gamePanel;
    private GameStateManager currentState;

    public GameLoop(GamePanel gamePanel, GameStateManager currentState) {
        this.gamePanel = gamePanel;
        this.currentState = currentState;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (currentState.getCurrentState() == Constants.StateID.PLAYING) {
            this.gamePanel.performLoopOperations();
        } else if (currentState.getCurrentState() == Constants.StateID.DESTROYED) {
            System.exit( 0 );
        }
    }

    public void doOneLoop() {
        gamePanel.performLoopOperations();
    }

}
