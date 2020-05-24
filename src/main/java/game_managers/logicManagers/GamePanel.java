package game_managers.logicManagers;

import Constants.Constants;
import Listeners.GameEventListener;
import Listeners.GameLoop;
import Listeners.Mouse;
import UI_components.UIConsole;
import UI_components.UItowersManager;
import ecs_container.Actors.Player;
import events.KeyHandlerManager;
import factories.EnemyFactory;
import factories.GameTowerFactory;
import factories.ProjectileFactory;
import game_managers.db.DBManager;
import graphic_context.MapManager;
import graphic_context.MouseIcon;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class GamePanel extends JPanel {
    private Font          textFont;
    private Timer         timer;
    private GameMainFrame gameFrame;

    private Player              player              = null;
    private WaveManager         waveManager         = null;
    private KeyHandlerManager   keyHandlerManager   = null;
    private MapManager          mapManager          = null;
    private UItowersManager     uItowersManager     = null;
    private TowerManager        towerManager        = null;
    private GameStateManager    stateManager        = null;
    private AccelerationManager accelerationManager = null;
    private EnemyManager        enemyManager        = null;
    private ClockManager        clockManager        = null;

    private MouseIcon mouseIcon;
    private long      gameSpeed = 0;
    private Long      lastTime  = 0L;
    private float     fpsRate   = 0f;

    public void gameSetup(HashMap< String, Object > dataSet, GameMainFrame frame) throws SQLException, ClassNotFoundException {
        this.gameFrame = frame;

        if (dataSet == null) {
            setLayout( null );
            initializeVariables();
            initializeLayout();
        } else {
            setLayout( null );
            initializeVariablesFromUpdate( dataSet );
            initializeLayout();
        }

        this.gameFrame.addKeyListener( this.keyHandlerManager );
        Mouse mouse = new Mouse( this );
        addMouseListener( mouse );
        addMouseMotionListener( mouse );
    }

    public GamePanel(GameMainFrame frame) {
        initializeGame();
    }

    public AccelerationManager getAccelerationManager() {
        return accelerationManager;
    }

    public void setAccelerationManager(AccelerationManager accelerationManager) {
        this.accelerationManager = accelerationManager;
    }

    public void initializeGame() {
        this.stateManager = GameStateManager.getInstance();
        this.timer = new Timer(
                Constants.GAME_SPEED,
                new GameLoop( this, this.stateManager )
        );

        this.accelerationManager = AccelerationManager.getInstance(
                this.gameSpeed,
                timer
        );
        this.stateManager.setCurrentState( Constants.StateID.INITIALIZED );
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public void setTowerManager(TowerManager towerManager) {
        this.towerManager = towerManager;
    }

    public MouseIcon getMouseIcon() {
        return mouseIcon;
    }

    public void setMouseIcon(MouseIcon mouseIcon) {
        this.mouseIcon = mouseIcon;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public void setMapManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public UItowersManager getuItowersManager() {
        return uItowersManager;
    }

    public void setuItowersManager(UItowersManager uItowersManager) {
        this.uItowersManager = uItowersManager;
    }

    public void initializeLayout() {
        setVisible( true );
        addKeyListener( new GameEventListener( this ) );  // listener = the subscriber, addKeyListener = subscribeToObservableObj
        setFocusable( true );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent( g );

        setFont(
                textFont
        );

        g.clearRect(
                0,
                0,
                this.gameFrame.getWidth(),
                this.gameFrame.getHeight()
        );

        g.fillRect(
                0,
                0,
                this.gameFrame.getWidth(),
                this.gameFrame.getHeight()
        );

        g.drawImage(
                new ImageIcon( Constants.IN_GAME_MENU_BACKGROUND ).getImage(),
                Constants.IN_GAME_MENU_X,
                Constants.IN_GAME_MENU_Y,
                Constants.IN_GAME_MENU_WIDTH,
                Constants.IN_GAME_MENU_HEIGHT,
                null
        );

        this.mapManager.render( g );
        this.uItowersManager.render( g );

        this.towerManager.render( g );
        this.enemyManager.render( g );

        try {
            drawUI( g );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        Toolkit.getDefaultToolkit().sync();
        lastTime = System.nanoTime();
    }

    private void initializeVariables() throws SQLException, ClassNotFoundException {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );

        textFont = Constants.KENVECTOR_20;

        this.clockManager = ClockManager.getInstance();
        this.player = Player.getInstance(
                Constants.INITIAL_NUMBER_OF_LIVES,
                Constants.INITIAL_NUMBER_OF_LIVES,
                400,
                clockManager
        );

        clockManager.addClock( UIConsole.getUpdateClock() );
        EnemyFactory.setClockManager( clockManager );
        GameTowerFactory.setClockManager( clockManager );
        ProjectileFactory.setClockManager( clockManager );

        this.keyHandlerManager = new KeyHandlerManager( this );

        this.mapManager = MapManager.getInstance( gameFrame.getDbManager() );
        // this.mapManager.loadMapFromTextFile( Constants.LEVEL_1_MAP_CONFIG_TXT );
        this.mapManager.loadMapFromDB( 1 );

        Player.setFortressX( this.mapManager.getAllyKeepCoordinates().getxCoord() );
        Player.setFortressY( this.mapManager.getAllyKeepCoordinates().getyCoord() );

        this.uItowersManager = UItowersManager.getInstance( 1 );
        this.towerManager = TowerManager.getInstance( mapManager );
        this.enemyManager = EnemyManager.getInstance( mapManager );
        this.waveManager = WaveManager.getInstance(
                mapManager,
                towerManager,
                enemyManager,
                clockManager,
                1
        );

        GameTowerFactory.setEnemyManager( enemyManager );
        this.mouseIcon = MouseIcon.generateMouseIcon();
        this.gameSpeed = Constants.GAME_SPEED;
    }

    private void initializeVariablesFromUpdate(HashMap< String, Object > dataSet) throws SQLException, ClassNotFoundException {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );

        textFont = Constants.KENVECTOR_20;

        this.clockManager = ClockManager.getInstance();
        this.player = Player.getInstance(
                ( Integer ) dataSet.get( "LIFE" ),
                Constants.INITIAL_NUMBER_OF_LIVES,
                ( Integer ) dataSet.get( "MONEY" ),
                clockManager
        );

        Player.setCurrentLevel( ( Integer ) dataSet.get( "LEVEL" ) );
        Player.setScore( ( Integer ) dataSet.get( "SCORE" ) );

        clockManager.addClock( UIConsole.getUpdateClock() );
        EnemyFactory.setClockManager( clockManager );
        GameTowerFactory.setClockManager( clockManager );
        ProjectileFactory.setClockManager( clockManager );

        this.keyHandlerManager = new KeyHandlerManager( this );

        this.mapManager = MapManager.getInstance( gameFrame.getDbManager() );
        // this.mapManager.loadMapFromTextFile( Constants.LEVEL_2_MAP_CONFIG_TXT );
        this.mapManager.loadMapFromDB(
                ( Integer ) dataSet.get( "LEVEL" )
        );

        Player.setFortressX( this.mapManager.getAllyKeepCoordinates().getxCoord() );
        Player.setFortressY( this.mapManager.getAllyKeepCoordinates().getyCoord() );

        this.uItowersManager = UItowersManager.getInstance( 1 );
        this.enemyManager = EnemyManager.getInstance( mapManager );
        this.enemyManager.deserializeEnemies( ( String ) dataSet.get( "MOBS" ) );

        GameTowerFactory.setEnemyManager( enemyManager );
        this.towerManager = TowerManager.getInstance( mapManager );
        this.towerManager.deserializeTowers( ( String ) dataSet.get( "TOWERS" ) );

        this.waveManager = WaveManager.getInstance(
                mapManager,
                towerManager,
                enemyManager,
                clockManager,
                ( Integer ) dataSet.get( "LEVEL" )
        );
        this.waveManager.setSpawnEnemies( false );

        this.mouseIcon = MouseIcon.generateMouseIcon();
        this.gameSpeed = Constants.GAME_SPEED;
    }


    public synchronized void startGame() {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );

        this.stateManager.setCurrentState( Constants.StateID.PLAYING );
        this.timer.start();
    }

    public GameStateManager getStateManager() {
        return stateManager;
    }

    public void performLoopOperations() {
        lastTime = System.nanoTime();
        try {
            update();
        } catch ( SQLException | ClassNotFoundException throwables ) {
            throwables.printStackTrace();
        }
        repaint();
    }

    public void update() throws SQLException, ClassNotFoundException {
        clockManager.update();
        waveManager.update();
        towerManager.update();
        enemyManager.update();

        if (enemyManager.numberOfActiveEnemies() == 0) {
            waveManager.loadNextLevel();

            if (waveManager.getCurrentLevel() == Constants.MAX_LEVEL + 1) {
                this.stateManager.setCurrentState( Constants.StateID.PAUSED );

                Object[] options = {
                        "Yes, please",
                };
                int userInput = JOptionPane.showOptionDialog(
                        this,
                        "Do you want to exit?",
                        "YOU HAVE WON THE GAME",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        new ImageIcon( Constants.QUESTION_MARK_URL ),
                        options,
                        options[0]
                );
                if (userInput == 0) {
                    DBManager dbManager = gameFrame.getDbManager();
                    try {
                        dbManager.INSERTIntoHighScores( Player.getScore() );
                    } catch ( SQLException | ClassNotFoundException throwables ) {
                        throwables.printStackTrace();
                    }
                    stateManager.setCurrentState( Constants.StateID.DESTROYED );
                }
            }
        }

        UIConsole.update();

        if (Player.getLives() <= 0) {
            Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                    "." +
                    new Exception().getStackTrace()[1].getMethodName() +
                    "()!"
            );

            this.stateManager.setCurrentState( Constants.StateID.PAUSED );
            System.out.println( "YOU HAVE LOST" );

            Object[] options = {
                    "Yes, please",
            };
            int userInput = JOptionPane.showOptionDialog(
                    this,
                    "Do you want to exit?",
                    "YOU HAVE LOST",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon( Constants.QUESTION_MARK_URL ),
                    options,
                    options[0]
            );
            if (userInput == 0) {
                DBManager dbManager = gameFrame.getDbManager();
                try {
                    dbManager.INSERTIntoHighScores( Player.getScore() );
                } catch ( SQLException | ClassNotFoundException throwables ) {
                    throwables.printStackTrace();
                }
                stateManager.setCurrentState( Constants.StateID.DESTROYED );
            }
        }
    }

    public void drawUI(Graphics g) throws IOException {

        Player.uiHeartBar.render( g );
        Player.uiDollarSign.render( g );
        UIConsole.render( g );
        g.drawImage(
                new ImageIcon( Constants.IN_GAME_UPGRADE_BUTTON_URL ).getImage(),
                Constants.IN_GAME_UPGRADE_BUTTON_X,
                Constants.IN_GAME_UPGRADE_BUTTON_Y,
                Constants.IN_GAME_UPGRADE_BUTTON_WIDTH,
                Constants.IN_GAME_UPGRADE_BUTTON_HEIGHT,
                null
        );
        g.drawImage(
                new ImageIcon( Constants.SELL_BUTTON_URL ).getImage(),
                Constants.IN_GAME_SELL_BUTTON_X,
                Constants.IN_GAME_SELL_BUTTON_Y,
                Constants.IN_GAME_SELL_BUTTON_WIDTH,
                Constants.IN_GAME_SELL_BUTTON_HEIGHT,
                null
        );

        /*
        try (FileInputStream fileInputStream = new FileInputStream( new File( Constants.UBUNTU_FONT_BOLD ) )) {
            g.setFont(
                    Font.createFont(
                            Font.TRUETYPE_FONT,
                            fileInputStream ).deriveFont( 40.0f )
            );
        } catch ( FontFormatException | IOException e ) {
            e.printStackTrace();
        }

         */

        g.setFont( Constants.UBUNTU_FONT_BOLD_40 );

        g.drawString(
                Constants.UPGRADE_TEXT,
                Constants.IN_GAME_UPGRADE_BUTTON_X + Constants.UPGRADE_TEXT_X_PADDING,
                Constants.IN_GAME_UPGRADE_BUTTON_Y + Constants.UPGRADE_TEXT_Y_PADDING
        );
        g.drawString(
                Constants.SELL_TEXT,
                Constants.IN_GAME_SELL_BUTTON_X + Constants.SELL_TEXT_X_PADDING,
                Constants.IN_GAME_SELL_BUTTON_Y + Constants.SELL_TEXT_Y_PADDING
        );

        g.drawLine(
                Constants.IN_GAME_UPGRADE_BUTTON_X - 50,
                Constants.IN_GAME_UPGRADE_BUTTON_Y + 201,
                Constants.IN_GAME_UPGRADE_BUTTON_X - 30 + 600,
                Constants.IN_GAME_UPGRADE_BUTTON_Y + 201
        );

        fpsRate = ( float ) ( Constants.ONE_SECOND / ( System.nanoTime() - lastTime ) );

        /*
        try (FileInputStream fileInputStream = new FileInputStream( new File( Constants.KENVECTOR_FUTURE_THIN_URL ) )) {
            g.setFont(
                    Font.createFont(
                            Font.TRUETYPE_FONT,
                            fileInputStream ).deriveFont( 60.0f )
            );
        } catch ( FontFormatException | IOException e ) {
            e.printStackTrace();
        }
         */

        g.setFont( Constants.KENVECTOR_60 );

        g.setColor( Constants.dollarSign );
        g.drawString(
                "SCORE: " + String.valueOf( ( int ) Player.getScore() ),
                Constants.SCORE_X,
                Constants.SCORE_Y
        );

        g.setColor( Constants.dollarSign );
        g.drawString(
                "LEVEL: " + String.valueOf( ( int ) Player.getCurrentLevel() ),
                Constants.LEVEL_X,
                Constants.LEVEL_Y
        );

        g.setColor( Constants.dollarSign );
        g.drawString(
                "[FPS] RATE: " + String.valueOf( ( int ) fpsRate ),
                Constants.FPS_RATE_X + Constants.FPS_RATE_X_PADDING,
                Constants.FPS_RATE_Y + Constants.FPS_RATE_Y_PADDING
        );
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public Player getPlayer() {
        return player;
    }
}

