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
import graphic_context.MapManager;
import graphic_context.MouseIcon;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GamePanel extends JPanel {

    private boolean              toDeleteDesTowers = false;
    private ImageIcon            backgroundImage;
    private Font                 textFont;
    private Timer                timer;
    private Thread               gameThread;
    private GameMainFrame        gameFrame;
    private Constants.HandHolder handHolder;

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
    private boolean   gameIsPaused    = true;
    private long      gameSpeed       = 0;
    private Long      updatesPerFrame = 0L;
    private Long      lastTime        = 0L;
    private float     fpsRate         = 0f;

    public GamePanel(GameMainFrame frame) {
        setLayout( null );
        initializeVariables();
        initializeGame();
        initializeLayout();

        this.gameFrame = frame;
        this.gameFrame.addKeyListener( this.keyHandlerManager );

        Mouse mouse = new Mouse( this );
        addMouseListener( mouse );
        addMouseMotionListener( mouse );
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

        drawUI( g );
        Toolkit.getDefaultToolkit().sync();
        lastTime = System.nanoTime();
    }

    private void initializeVariables() {
        try {
            textFont = Font.createFont( Font.TRUETYPE_FONT, new FileInputStream( new File( Constants.KENVECTOR_FUTURE_THIN_URL ) ) ).deriveFont( 20.0f );
        } catch ( FileNotFoundException e ) {


            e.printStackTrace();
        } catch ( FontFormatException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        this.clockManager = ClockManager.getInstance();

        this.player = Player.getInstance(
                40,
                40,
                400,
                clockManager
        );

        clockManager.addClock( UIConsole.getUpdateClock() );
        EnemyFactory.setClockManager( clockManager );
        GameTowerFactory.setClockManager( clockManager );
        ProjectileFactory.setClockManager( clockManager );

        this.keyHandlerManager = new KeyHandlerManager( this );

        this.mapManager = MapManager.getInstance( Constants.LEVEL_1_MAP_CONFIG_TXT );
        Player.setFortressX( this.mapManager.getAllyKeepCoordinates().getxCoord() );
        Player.setFortressY( this.mapManager.getAllyKeepCoordinates().getyCoord() );

        this.handHolder = new Constants.HandHolder();
        this.uItowersManager = UItowersManager.getInstance( 1 );
        this.towerManager = TowerManager.getInstance( mapManager );
        this.enemyManager = EnemyManager.getInstance( mapManager );
        this.waveManager = WaveManager.getInstance( mapManager, enemyManager, clockManager );
        GameTowerFactory.setEnemyManager( enemyManager );
        this.mouseIcon = MouseIcon.generateMouseIcon();
        this.gameSpeed = Constants.GAME_SPEED;

        // !
    }

    public synchronized void startGame() {
        this.stateManager.setCurrentState( Constants.StateID.PLAYING );
        this.timer.start();
    }

    public GameStateManager getStateManager() {
        return stateManager;
    }

    public void performLoopOperations() {
        lastTime = System.nanoTime();
        update();
        repaint();
    }

    public void update() {
        clockManager.update();
        waveManager.update();
        towerManager.update();
        enemyManager.update();
        UIConsole.update();

        if (toDeleteDesTowers == false) {
            towerManager.deserializeTowers( "|ArcaneTower 588 168 3 CANNON_TOWER|CraneTower 798 168 1 ARCANE_TOWER|ZombieTower 294 168 2 CRANE_TOWER" );
            toDeleteDesTowers = true;
        }
    }

    public void drawUI(Graphics g) {
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

        try {
            g.setFont(
                    Font.createFont(
                            Font.TRUETYPE_FONT,
                            new FileInputStream( new File( Constants.UBUNTU_FONT_BOLD ) ) ).deriveFont( 40.0f )
            );
        } catch ( FontFormatException | IOException e ) {
            e.printStackTrace();
        }
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
        try {
            g.setFont(
                    Font.createFont(
                            Font.TRUETYPE_FONT,
                            new FileInputStream( new File( Constants.KENVECTOR_FUTURE_THIN_URL ) ) ).deriveFont( 60.0f )
            );
        } catch ( FontFormatException | IOException e ) {
            e.printStackTrace();
        }
        g.drawString(
                "[FPS] RATE: " + String.valueOf( ( int ) fpsRate ),
                Constants.FPS_RATE_X + Constants.FPS_RATE_X_PADDING,
                Constants.FPS_RATE_Y + Constants.FPS_RATE_Y_PADDING
        );
    }
}

