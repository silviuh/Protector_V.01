package Listeners;

import game_managers.logicManagers.GamePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameEventListener extends KeyAdapter {

    private GamePanel board;

    public GameEventListener(GamePanel board) {
        this.board = board;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
