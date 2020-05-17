package game_managers.logicManagers;

import Constants.Constants;
import ecs_container.towers.Tower;
import factories.ClockFactory;
import factories.EnemyFactory;
import graphic_context.MapManager;
import utilities.Clock;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class WaveManager {
    private static volatile WaveManager                           waveManager;
    private                 EnemyManager                          enemyManager;
    private                 ClockManager                          clockManager;
    private                 MapManager                            mapManager;
    private static          ReentrantLock                         singletonLock;
    private                 HashMap< String, Clock >              wavesClocks;
    private                 HashMap< String, ArrayList< Tower > > container;

    private long                totalNrOfEnemies        = 0;
    private long                currentNrOfEnemies      = 0;
    private long                totalNumberOfEnemyTypes = Constants.enemyType.values().length;
    private Constants.enemyType currentEnemyType;

    private WaveManager(MapManager mapManager, EnemyManager enemyManager, ClockManager clockManager) {
        this.mapManager = mapManager;
        this.enemyManager = enemyManager;
        this.clockManager = clockManager;
        container = new HashMap< String, ArrayList< Tower > >();
        wavesClocks = new HashMap< String, Clock >();
        initialize();
    }

    public void initialize() {
        Clock clockWave1 = ClockFactory.createInstance( Constants.clockType.WAVE_1 );
        wavesClocks.put(
                "WAVE_1", clockWave1
        );
        clockManager.addClock( clockWave1 );
        currentNrOfEnemies = 0;
        totalNrOfEnemies = 20;
    }

    public static WaveManager getInstance(MapManager mapManager, EnemyManager enemyManager, ClockManager clockManager) {
        singletonLock = new ReentrantLock();
        if (waveManager == null) {
            try {
                singletonLock.lock();
                waveManager = new WaveManager(
                        mapManager,
                        enemyManager,
                        clockManager
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return waveManager;
    }

    public void update() {
        if (currentNrOfEnemies < totalNrOfEnemies) {
            currentEnemyType = Constants.enemyType.values()[new Random().nextInt( ( int ) totalNumberOfEnemyTypes )];
            if (wavesClocks.get( "WAVE_1" ).mayUpate()) {
                System.out.println( "Enemy created..." );
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

    public void render(Graphics graphics) {
        for (ArrayList< Tower > towerList : container.values()) {
            for (Tower tower : towerList) {
                tower.render( graphics );
            }
        }
    }
}
