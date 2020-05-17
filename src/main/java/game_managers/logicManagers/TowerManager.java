package game_managers.logicManagers;

import Constants.Constants;
import ecs_container.towers.Tower;
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
        if (container.get( towerName ) == null) {
            container.put( towerName, new ArrayList< Tower >() );
        }

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
}
