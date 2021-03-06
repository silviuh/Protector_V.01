package game_managers.logicManagers;

import Constants.Constants;
import game_managers.db.DBManager;
import game_managers.menus.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * the top level container for the UI arhitecture
 */
public class GameMainFrame extends JFrame {
    private JPanel        mainPanelContainer = null;
    private GamePanel     gamePanel          = null;
    private MainMenu      mainMenuPanel      = null;
    private HelpMenu      helpMenu           = null;
    private HighScoreMenu highScoreMenu      = null;
    private LoadGameMenu  loadGameMenu       = null;
    private InGameMenu    inGameMenu         = null;
    private CardLayout    panelSwitcher      = null;

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
        highScoreMenu = new HighScoreMenu( this );

        loadGameMenu = new LoadGameMenu( this );
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
                "LOAD_GAME_PANEL",
                loadGameMenu
        );
        panels.put(
                "HIGH_SCORES_PANEL",
                highScoreMenu
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

    /**
     * the games used a Panel container and a card Layout to switch between panels
     * @throws IOException
     */
    public void initializeLayout() throws IOException {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );

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
                panels.get( "LOAD_GAME_PANEL" ),
                "LOAD_GAME_PANEL"
        );
        mainPanelContainer.add(
                panels.get( "HIGH_SCORES_PANEL" ),
                "HIGH_SCORES_PANEL"
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

        this.setExtendedState( JFrame.MAXIMIZED_BOTH );
        this.setResizable( true );
        this.setUndecorated( true );
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

    public void initalizeGame(HashMap< String, Object > dataSet) {
        if (dataSet != null) {
            this.gamePanel = new GamePanel( this );
        }
    }
}
