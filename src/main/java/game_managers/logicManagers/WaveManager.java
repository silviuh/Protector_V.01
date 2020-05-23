package game_managers.logicManagers;

import Constants.Constants;
import ecs_container.Actors.Player;
import ecs_container.towers.Tower;
import factories.ClockFactory;
import factories.EnemyFactory;
import graphic_context.MapManager;
import utilities.Clock;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class WaveManager {
    private                 int                         currentLevel;
    private                 int                         maxLevel;
    private                 boolean                     deleteFlagValue = false;
    private static volatile WaveManager                 waveManager;
    private                 EnemyManager                enemyManager;
    private                 TowerManager                towerManager;
    private                 ClockManager                clockManager;
    private                 MapManager                  mapManager;
    private static          ReentrantLock               singletonLock;
    private                 HashMap< Integer, Clock >   wavesClocks;
    private                 HashMap< Integer, Integer > levelConfigurations;

    private long                totalNrOfEnemies        = 0;
    private long                currentNrOfEnemies      = 0;
    private long                totalNumberOfEnemyTypes = Constants.enemyType.values().length;
    private Constants.enemyType currentEnemyType;

    private WaveManager(MapManager mapManager, EnemyManager enemyManager, TowerManager towerManager, ClockManager clockManager, int level) {
        this.mapManager = mapManager;
        this.enemyManager = enemyManager;
        this.towerManager = towerManager;
        this.clockManager = clockManager;

        this.currentLevel = level;
        this.maxLevel = Constants.MAX_LEVEL;
        initialize();
    }

    public void initialize() {
        levelConfigurations = new HashMap<>( 3 );
        // !
        levelConfigurations.put( 1, 2 );
        levelConfigurations.put( 2, 2 );
        levelConfigurations.put( 3, 2 );

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

    public static WaveManager getInstance(MapManager mapManager, TowerManager towerManager, EnemyManager enemyManager, ClockManager clockManager, int level) {
        singletonLock = new ReentrantLock();
        if (waveManager == null) {
            try {
                singletonLock.lock();
                waveManager = new WaveManager(
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

    public void loadNextLevel() throws SQLException, ClassNotFoundException {
        currentLevel++;
        if (currentLevel == maxLevel + 1) {
            System.out.println( "YOU WON" );
            // !
        } else {
            Player.setCurrentLevel( currentLevel );
            Player.resetLife();
            towerManager.deleteContainer();
            enemyManager.deleteContainer();
            mapManager.deleteContainer();
            mapManager.loadMapFromDB( currentLevel );
            currentNrOfEnemies = 0;
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void update() {
        if (currentLevel < Constants.MAX_LEVEL + 1) {
            if (currentNrOfEnemies < levelConfigurations.get( currentLevel )) {
                currentEnemyType = Constants.enemyType.values()[new Random().nextInt( ( int ) totalNumberOfEnemyTypes - 1 )]; // -1 as we don't want the undefined type too
                if (wavesClocks.get( currentLevel ).mayUpate()) {
                    // System.out.println( "Enemy created..." );
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
        /*
        if (deleteFlagValue == false) {
            enemyManager.deserializeEnemies(
                    "|Devil 40.0 126 168|Owl 80.0 42 42|Slime 40.0 84 42|Sonic 90.0 42 84"
            );
            deleteFlagValue = true;
        }
         */
    }

    public void setLevel(int level) {
        this.currentLevel = level;
    }
}
