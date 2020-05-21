package game_managers.menus;

import Constants.Constants;
import ecs_container.Actors.Player;
import game_managers.logicManagers.AccelerationManager;
import game_managers.logicManagers.GameMainFrame;
import game_managers.logicManagers.GamePanel;
import game_managers.logicManagers.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class InGameMenu {
    private static          ReentrantLock singletonLock;
    private static volatile InGameMenu    inGameMenu;
    private                 GameMainFrame mainFrame;
    private                 JMenuBar      menuBar;
    private                 JMenu         menu, submenu;
    private GameStateManager    stateManager;
    private AccelerationManager accelerationManager;
    private List< JMenuItem >   menuItems;

    private InGameMenu(GameMainFrame mainFrame, GameStateManager stateManager, AccelerationManager accelerationManager) {
        this.menuItems = new ArrayList< JMenuItem >();
        this.stateManager = stateManager;
        this.accelerationManager = accelerationManager;
        this.mainFrame = mainFrame;
        JMenuItem currentMenuItem;
        menuBar = new JMenuBar();

        menu = new JMenu( "Options" );
        menu.setMnemonic( KeyEvent.VK_ESCAPE );
        menu.getAccessibleContext().setAccessibleDescription( "In-game options menu" );
        menuBar.add( menu );


        menu.addSeparator();
        submenu = new JMenu( "Speed" );
        submenu.setOpaque( true );
        submenu.setBackground( Color.WHITE );
        submenu.setIcon(
                new ImageIcon( Constants.GAME_SPEED_BUTTON )
        );
        submenu.setMnemonic( KeyEvent.VK_S );


        currentMenuItem = new JMenuItem(
                "Increase Speed",
                new ImageIcon( Constants.INCREASE_GAME_SPEED_1_URL )
        );
        currentMenuItem.setAccelerator( KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK ) );
        currentMenuItem.setOpaque( true );
        currentMenuItem.setBackground( Color.WHITE );
        currentMenuItem.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accelerationManager.doubleAcceleration();
            }
        } );
        submenu.add( currentMenuItem );


        currentMenuItem = new JMenuItem(
                "Decrease Speed",
                new ImageIcon( Constants.DECREASE_GAME_SPEED_1_URL )
        );
        currentMenuItem.setAccelerator( KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK ) );
        currentMenuItem.setOpaque( true );
        currentMenuItem.setBackground( Color.WHITE );
        currentMenuItem.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accelerationManager.dichotomizeAcceleration();
            }
        } );
        submenu.add( currentMenuItem );


        menu.add( submenu );


        currentMenuItem = new JMenuItem(
                "Pause",
                new ImageIcon( Constants.PAUSE_BUTTON_1_URL )
        );
        currentMenuItem.setSize( 32, 32 );
        currentMenuItem.setMnemonic( KeyEvent.VK_B );
        currentMenuItem.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stateManager.getCurrentState() == Constants.StateID.PAUSED)
                    stateManager.setCurrentState( Constants.StateID.PLAYING );
                else if (stateManager.getCurrentState() == Constants.StateID.PLAYING)
                    stateManager.setCurrentState( Constants.StateID.PAUSED );
            }
        } );
        currentMenuItem.setOpaque( true );
        currentMenuItem.setBackground( Color.WHITE );
        menuItems.add( currentMenuItem );


        currentMenuItem = new JMenuItem(
                "Save Game",
                new ImageIcon( Constants.SAVE_GAME_ICON_URL )
        );
        currentMenuItem.setSize( 32, 32 );
        currentMenuItem.setMnemonic( KeyEvent.VK_B );
        currentMenuItem.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel gamePanel = mainFrame.getGamePanel();
                try {
                    mainFrame.getDbManager().openConnection();
                    mainFrame.getDbManager().INSERTIntoGameSavings(
                            gamePanel.getEnemyManager().serializeEnemies(),
                            gamePanel.getTowerManager().serializeTowers(),
                            Player.getLives(),
                            Player.getCurrentScore(),
                            0, // !
                            Player.getCurrentAmountOfMoney()
                    );
                    mainFrame.getDbManager().closeConnection();
                } catch ( SQLException | ClassNotFoundException throwables ) {
                    throwables.printStackTrace();
                }
            }
        } );
        currentMenuItem.setOpaque( true );
        currentMenuItem.setBackground( Color.WHITE );
        menuItems.add( currentMenuItem );


        currentMenuItem = new JMenuItem(
                "Exit",
                new ImageIcon( Constants.EXIT_BUTTON_1_URL )
        );
        currentMenuItem.setMnemonic( KeyEvent.VK_D );
        currentMenuItem.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] possibilities = { "Yes", "No" };
                Object[] options = {
                        "Yes, please",
                        "No way!"
                };

                int userInput = JOptionPane.showOptionDialog(
                        mainFrame,
                        "Do you really want to exit?",
                        "Select an option",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        new ImageIcon( Constants.QUESTION_MARK_URL ),
                        options,
                        options[0]
                );
                if (userInput == 0) {
                    stateManager.setCurrentState( Constants.StateID.DESTROYED );
                }
                return;
            }
        } );
        currentMenuItem.setOpaque( true );
        currentMenuItem.setBackground( Color.WHITE );
        menuItems.add( currentMenuItem );


        for (JMenuItem item : menuItems) {
            menu.add( item );
        }
        mainFrame.setJMenuBar( menuBar );
    }

    public static InGameMenu getInstance(GameMainFrame mainFrame, GameStateManager stateManager, AccelerationManager accelerationManager) {
        singletonLock = new ReentrantLock();
        if (inGameMenu == null) {
            try {
                singletonLock.lock();
                inGameMenu = new InGameMenu(
                        mainFrame,
                        stateManager,
                        accelerationManager
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return inGameMenu;
    }


}
