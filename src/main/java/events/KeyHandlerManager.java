package events;

import game_managers.logicManagers.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandlerManager implements KeyListener {

    private GamePanel gamePanel;

    public KeyHandlerManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == e.VK_ESCAPE){
            System.out.println("iesire din joc");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
