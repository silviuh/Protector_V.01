package game_managers.logicManagers;

import Constants.Constants;
import ecs_container.Actors.enemies.Enemy;
import ecs_container.towers.ArcaneTower;
import ecs_container.towers.Tower;
import factories.EnemyFactory;
import factories.GameTowerFactory;
import graphic_context.MapManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TowerManager {
    private static volatile TowerManager                          towerManager;
    private static          ReentrantLock                         singletonLock;
    private                 HashMap< String, ArrayList< Tower > > container;

    private TowerManager(MapManager mapManager) {
        container = new HashMap< String, ArrayList< Tower > >();
    }

    public static TowerManager getInstance(MapManager mapManager) {
        singletonLock = new ReentrantLock();
        if (towerManager == null) {
            try {
                singletonLock.lock();
                towerManager = new TowerManager(
                        mapManager
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return towerManager;
    }


    public void addTower(Tower tower, String towerName) {
        container.computeIfAbsent( towerName, k -> new ArrayList< Tower >() );
        container.get( towerName ).add( tower );
    }

    public void render(Graphics graphics) {
        for (ArrayList< Tower > towerList : container.values()) {
            for (Tower tower : towerList) {
                if (tower.isActive())
                    tower.render( graphics );
            }
        }
    }

    public void update() {
        for (ArrayList< Tower > towerList : container.values()) {
            for (Tower tower : towerList) {
                if (tower.isActive())
                    tower.update();
            }
        }
    }

    public Constants.PairOfCoordinates returnTowerCoordinates(int mouseX, int mouseY) {
        for (ArrayList< Tower > towerList : container.values()) {
            for (Tower tower : towerList) {
                if (tower.isActive()) {
                    if (
                            tower.getxPos() <= mouseX && ( tower.getxPos() + Constants.TILE_SIZE ) >= mouseX &&
                                    tower.getyPos() <= mouseY && ( tower.getyPos() + Constants.TILE_SIZE ) >= mouseY
                    ) {
                        return new Constants.PairOfCoordinates( tower.getxPos(), tower.getyPos() );
                    }
                }
            }
        }
        return null;
    }

    public void setVisibleRange(int towerX, int towerY) {
        for (ArrayList< Tower > towerList : container.values()) {
            for (Tower tower : towerList) {
                if (tower.isActive()) {
                    if (tower.getxPos() == towerX && tower.getyPos() == towerY) {
                        tower.setDrawRange( true );
                    }
                }
            }
        }
    }


    public void hideVisibleRange(int towerX, int towerY) {
        for (ArrayList< Tower > towerList : container.values()) {
            for (Tower tower : towerList) {
                if (tower.isActive()) {
                    if (tower.getxPos() == towerX && tower.getyPos() == towerY) {
                        tower.setDrawRange( false );
                    }
                }
            }
        }
    }

    public boolean towerHasRangeVisible(int towerX, int towerY) {
        for (ArrayList< Tower > towerList : container.values()) {
            for (Tower tower : towerList) {
                if (tower.isActive()) {
                    if (tower.getxPos() == towerX && tower.getyPos() == towerY) {
                        return tower.isDrawRange();
                    }
                }
            }
        }
        return false;
    }

    public Tower getTowerByCoordinates(int x, int y) {
        for (ArrayList< Tower > towerList : container.values()) {
            for (Tower tower : towerList) {
                if (tower.isActive()) {
                    if (x >= tower.getxPos() && x <= tower.getxPos() + Constants.TILE_SIZE
                            && y >= tower.getyPos() && y <= tower.getyPos() + Constants.TILE_SIZE) {
                        return tower;
                    }
                }
            }
        }
        return null;
    }

    public int getTowerByClassName(String towerName) {
        switch (towerName) {
            case "ArcaneTower": {
                return 0;
            }
            case "CannonTower": {
                return 1;
            }
            case "ZombieTower": {
                return 2;
            }
            case "CraneTower": {
                return 3;
            }
        }
        return -1;
    }

    public String getTowerUINameByClasssName(String towerName) {
        switch (towerName) {
            case "ArcaneTower": {
                return "ARCANE_TOWER";
            }
            case "CannonTower": {
                return "CANNON_TOWER";
            }
            case "ZombieTower": {
                return "ZOMBIE_TOWER";
            }
            case "CraneTower": {
                return "CRANE_TOWER";
            }
        }
        return "UNDEFINED";
    }

    public int getTowerTypeByClassName(String towerName) {
        switch (towerName) {
            case "ArcaneTower": {
                return 0;
            }
            case "CannonTower": {
                return 1;
            }
            case "ZombieTower": {
                return 2;
            }
            case "CraneTower": {
                return 3;
            }
        }
        return -1;
    }


    public String serializeTowers() {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );

        StringBuilder result = new StringBuilder();

        for (ArrayList< Tower > towerList : container.values()) {
            for (Tower tower : towerList) {
                if (tower.isActive()) {
                    result.append(
                            "|" +
                                    ( tower.getClass().getSimpleName() ) + " " +
                                    Integer.toString( tower.getxPos() ) + " " +
                                    Integer.toString( tower.getyPos() ) + " " +
                                    Integer.toString( tower.getLevel() ) + " " +
                                    getTowerUINameByClasssName( tower.getClass().getSimpleName() )
                    );
                }
            }
        }

        return result.toString();
    }

    public void deserializeTowers(String serializedContainer) {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );

        String[] towersTokens = serializedContainer.split( "\\|" );

        for (String towersToken : towersTokens) {
            if (towersToken.length() > 1) {
                String[] values = towersToken.split( " " );
                addTower(
                        GameTowerFactory.createDeserializedInstance(
                                Integer.parseInt( values[1] ),
                                Integer.parseInt( values[2] ),
                                Constants.towerType.values()[getTowerTypeByClassName( values[0] )],
                                Integer.parseInt( values[3] )
                        ),
                        values[4]
                );
            }
        }
    }

    public void deleteContainer() {
        this.container.clear();
    }
}
