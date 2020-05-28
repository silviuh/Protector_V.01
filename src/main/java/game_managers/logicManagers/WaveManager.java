package game_managers.logicManagers;

import Constants.Constants;
import ecs_container.Actors.Player;
import ecs_container.towers.Tower;
import factories.ClockFactory;
import factories.EnemyFactory;
import game_managers.db.DBManager;
import graphic_context.MapManager;
import utilities.Clock;

import javax.swing.*;
import java.awt.*;
import java.lang.management.PlatformLoggingMXBean;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * responsible for managing levels
 */
public class WaveManager {
    private                 boolean                     spawnEnemies = true;
    private                 int                         currentLevel;
    private                 int                         maxLevel;
    private                 GamePanel                   gamePanel;
    private static volatile WaveManager                 waveManager;
    private                 EnemyManager                enemyManager;
    private                 TowerManager                towerManager;
    private                 ClockManager                clockManager;
    private                 MapManager                  mapManager;
    private static          ReentrantLock               singletonLock;
    private                 HashMap< Integer, Clock >   wavesClocks;
    private                 HashMap< Integer, Integer > levelConfigurations;

    private       long currentNrOfEnemies      = 0;
    private final long totalNumberOfEnemyTypes = Constants.enemyType.values().length;

    private WaveManager(GamePanel gamePanel, MapManager mapManager, EnemyManager enemyManager,
                        TowerManager towerManager, ClockManager clockManager, int level) {
        this.gamePanel = gamePanel;
        this.mapManager = mapManager;
        this.enemyManager = enemyManager;
        this.towerManager = towerManager;
        this.clockManager = clockManager;

        this.currentLevel = level;
        this.maxLevel = Constants.MAX_LEVEL;
        initialize();
    }

    /**
     * initialize the levelConfigurations
     */
    public void initialize() {
        levelConfigurations = new HashMap<>( Constants.NUMBER_OF_LEVELS );
        levelConfigurations.put( 1, Constants.NUMBER_OF_ENEMIES_LEVEL_1 );
        levelConfigurations.put( 2, Constants.NUMBER_OF_ENEMIES_LEVEL_2 );
        levelConfigurations.put( 3, Constants.NUMBER_OF_ENEMIES_LEVEL_3 );

        wavesClocks = new HashMap< Integer, Clock >();
        Clock clockWave1 = ClockFactory.createInstance( Constants.clockType.WAVE_1 );
        wavesClocks.put(
                1, clockWave1
        );
        clockManager.addClock( clockWave1 );

        Clock clockWave2 = ClockFactory.createInstance( Constants.clockType.WAVE_2 );
        wavesClocks.put(
                2, clockWave2
        );
        clockManager.addClock( clockWave2 );

        Clock clockWave3 = ClockFactory.createInstance( Constants.clockType.WAVE_3 );
        wavesClocks.put(
                3, clockWave3
        );
        clockManager.addClock( clockWave3 );
    }

    public static WaveManager getInstance(GamePanel gamePanel, MapManager mapManager, TowerManager towerManager, EnemyManager enemyManager, ClockManager clockManager, int level) {
        singletonLock = new ReentrantLock();
        if (waveManager == null) {
            try {
                singletonLock.lock();
                waveManager = new WaveManager(
                        gamePanel,
                        mapManager,
                        enemyManager,
                        towerManager,
                        clockManager,
                        level
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return waveManager;
    }

    /**
     * loads the next level if the conditions are fullfilled
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void loadNextLevel() throws SQLException, ClassNotFoundException {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );
        currentLevel++;
        nextLevelConfig();

        if (currentLevel == maxLevel + 1) {
            Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                    "." +
                    new Exception().getStackTrace()[1].getMethodName() +
                    "()!"
            );
            System.out.println( "YOU WON" );
        } else {
            if (!spawnEnemies)
                spawnEnemies = true;
            Player.setCurrentLevel( currentLevel );
            Player.resetLife();

            switch (currentLevel) {
                case 2: {
                    Player.addMoney( Constants.LEVEL_2_INCOME );
                    break;
                }
                case 3: {
                    Player.addMoney( Constants.LEVEL_3_INCOME );
                    break;
                }
            }

            towerManager.deleteContainer();
            enemyManager.deleteContainer();
            mapManager.deleteContainer();
            mapManager.loadMapFromDB( currentLevel );
            Player.setFortressX( mapManager.getAllyKeepCoordinates().getyCoord() ); // inverse coordinates with regard to the plane position
            Player.setFortressY( mapManager.getAllyKeepCoordinates().getxCoord() );
            currentNrOfEnemies = 0;
        }
    }

    /**
     * displays dialog boxes according to the current progress in the game and changes the game state
     */
    public void nextLevelConfig() {
        int userInput = 0;
        this.gamePanel.getStateManager().setCurrentState( Constants.StateID.PAUSED );
        Object[] optionsForIntermediareLevels = {
                "Yes, please",
                "No, continue"
        };

        Object[] optionsForGameEndings = {
                "Yes, please"
        };

        String[] titles = { "YOU HAVE WON THE GAME", "YOU HAVE REACHED LEVEL: " };

        if (currentLevel == Constants.MAX_LEVEL + 1) {
            userInput = JOptionPane.showOptionDialog(
                    gamePanel,
                    "Do you want to exit?",
                    titles[0],
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon( Constants.QUESTION_MARK_URL ),
                    optionsForGameEndings,
                    optionsForGameEndings[0]
            );
        } else {
            userInput = JOptionPane.showOptionDialog(
                    gamePanel,
                    "Do you want to exit?",
                    titles[1] + currentLevel,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon( Constants.QUESTION_MARK_URL ),
                    optionsForIntermediareLevels,
                    optionsForIntermediareLevels[0]
            );
        }

        if (userInput == 0) {
            DBManager dbManager = gamePanel.getGameFrame().getDbManager();
            try {
                dbManager.INSERTIntoHighScores( Player.getScore() );
            } catch ( SQLException | ClassNotFoundException throwables ) {
                throwables.printStackTrace();
            }

            gamePanel.getStateManager().setCurrentState( Constants.StateID.DESTROYED );
        } else {
            gamePanel.getStateManager().setCurrentState( Constants.StateID.PLAYING );
        }
    }


    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * used to spawn enemies according to the current level
     */
    public void update() {
        if (spawnEnemies) {
            if (currentLevel < Constants.MAX_LEVEL + 1) {
                if (currentNrOfEnemies < levelConfigurations.get( currentLevel )) {
                    Constants.enemyType currentEnemyType = Constants.enemyType.values()[new Random().nextInt( ( int ) totalNumberOfEnemyTypes - 1 )]; // -1 as we don't want the undefined type too
                    if (wavesClocks.get( currentLevel ).mayUpate()) {
                        enemyManager.addEnemy(
                                EnemyFactory.createInstance(
                                        mapManager.getTile( mapManager.getEnemyStartingPoint() ), // 11
                                        currentEnemyType
                                ),
                                "ENEMY_1"
                        );
                        currentNrOfEnemies++;
                    }
                }
            }
        }
    }

    public void setLevel(int level) {
        this.currentLevel = level;
    }

    public void setSpawnEnemies(boolean spawnEnemies) {
        this.spawnEnemies = spawnEnemies;
    }
}
