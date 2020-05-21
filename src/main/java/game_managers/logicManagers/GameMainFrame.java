package game_managers.logicManagers;

import Constants.Constants;
import game_managers.db.DBManager;
import game_managers.menus.HelpMenu;
import game_managers.menus.InGameMenu;
import game_managers.menus.MainMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GameMainFrame extends JFrame {
    private JPanel     mainPanelContainer = null;
    private GamePanel  gamePanel          = null;
    private MainMenu   mainMenuPanel      = null;
    private HelpMenu   helpMenu           = null;
    private InGameMenu inGameMenu         = null;
    private CardLayout panelSwitcher      = null;

    private DBManager dbManager = null;

    private HashMap< String, JPanel > panels;
    Dimension screenDimension = null;

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public GameMainFrame(DBManager dbManager) {
        this.dbManager = dbManager;
        mainPanelContainer = new JPanel();
        gamePanel = new GamePanel( this );
        mainMenuPanel = new MainMenu( this );
        helpMenu = new HelpMenu( this );
        panelSwitcher = new CardLayout();
        panels = new HashMap< String, JPanel >();
        screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

        mainMenuPanel = MainMenu.getInstance( this );
        helpMenu = HelpMenu.getInstance( this );

        panels.put(
                "MAIN_MENU_PANEL",
                mainMenuPanel
        );
        panels.put(
                "HELP_PANEL",
                helpMenu
        );
        panels.put(
                "GAME_PANEL",
                gamePanel
        );

        try {
            initializeLayout();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public void initializeLayout() throws IOException {
        mainPanelContainer.setLayout( panelSwitcher );
        this.setTitle( Constants.TITLE );
        this.setExtendedState( MAXIMIZED_BOTH );
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );

        Cursor cursor = new Cursor( Cursor.CROSSHAIR_CURSOR );
        setCursor( cursor );

        mainPanelContainer.add(
                panels.get( "MAIN_MENU_PANEL" ),
                "MAIN_MENU_PANEL"
        );
        mainPanelContainer.add(
                panels.get( "HELP_PANEL" ),
                "HELP_PANEL"
        );
        mainPanelContainer.add(
                panels.get( "GAME_PANEL" ),
                "GAME_PANEL"
        );


        inGameMenu = InGameMenu.getInstance(
                this,
                gamePanel.getStateManager(),
                gamePanel.getAccelerationManager()
        );

        add( mainPanelContainer );

        // this.setSize( screenDimension.width, screenDimension.height );
        this.setExtendedState( JFrame.MAXIMIZED_BOTH );
        this.setResizable( true );
        this.setUndecorated( true );
        // GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
        // this.pack();
        this.setIconImage( ImageIO.read( new File( Constants.GAME_ICON ) ) );
        this.setVisible( true );
    }

    public void windowClosing(WindowEvent e) {
        dispose();
        System.exit( 0 );
    }

    public CardLayout getPanelSwitcher() {
        return panelSwitcher;
    }

    public JPanel getMainPanelContainer() {
        return mainPanelContainer;
    }

    public DBManager getDbManager() {
        return dbManager;
    }
}
