package factories;

import Constants.Constants;
import ecs_container.towers.*;
import game_managers.logicManagers.ClockManager;
import game_managers.logicManagers.EnemyManager;
import graphic_context.SpriteSheet;
import utilities.Clock;

/**
 * Factory class used for creating different types of towers.
 */
public class GameTowerFactory {
    static         EnemyManager enemyManager;
    private static ClockManager clockManager;

    private GameTowerFactory() {


    }

    public static void setClockManager(ClockManager clockManager) {
        GameTowerFactory.clockManager = clockManager;
    }

    public static void setEnemyManager(EnemyManager enemyManager) {
        GameTowerFactory.enemyManager = enemyManager;
    }

    public static Tower createDeserializedInstance(int xCoord, int yCoord, Constants.towerType towerType, int formerLevel) {
        Tower tower = null;
        Clock clock = null;

        switch (towerType) {
            case IN_GAME_CRANE_TOWER: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.CRANE
                );
                tower = new CraneTower(
                        xCoord,
                        yCoord,
                        SpriteContainerFactory.createTowerSprite( towerType ),
                        Constants.craneInner,
                        Constants.craneOuter,
                        Constants.craneText,
                        Constants.CRANE_TOWER_RANGE,
                        Constants.CRANE_NAME,
                        AttackBehaviours.createInstance(
                                Constants.attackBehaviour.CRANE
                        ),
                        clock,
                        enemyManager,
                        Constants.projectileType.CRANE_TRACKER
                );
                break;
            }
            case IN_GAME_ARCANE_TOWER: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.ARCANE
                );
                tower = new ArcaneTower(
                        xCoord,
                        yCoord,
                        SpriteContainerFactory.createTowerSprite( towerType ),
                        Constants.arcaneInner,
                        Constants.arcaneOuter,
                        Constants.arcaneText,
                        Constants.ARCANE_TOWER_RANGE,
                        Constants.ARCANE_NAME,
                        AttackBehaviours.createInstance(
                                Constants.attackBehaviour.ARCANE
                        ),
                        clock,
                        enemyManager,
                        Constants.projectileType.ARCANE_BLAST
                );
                break;
            }
            case IN_GAME_ZOMBIE_TOWER: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.ZOMBIE
                );
                tower = new ZombieTower(
                        xCoord,
                        yCoord,
                        SpriteContainerFactory.createTowerSprite( towerType ),
                        Constants.zombieInner,
                        Constants.zombieOuter,
                        Constants.zombieText,
                        Constants.ZOMBIE_TOWER_RANGE,
                        Constants.ZOMBIE_NAME,
                        AttackBehaviours.createInstance(
                                Constants.attackBehaviour.ZOMBIE
                        ),
                        clock,
                        enemyManager,
                        Constants.projectileType.ZOMBIE_MISSLE
                );
                break;
            }
            case IN_GAME_CANNON_TOWER: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.CANNON
                );
                tower = new CannonTower(
                        xCoord,
                        yCoord,
                        SpriteContainerFactory.createTowerSprite( towerType ),
                        Constants.cannonInner,
                        Constants.cannonOuter,
                        Constants.cannonText,
                        Constants.CANNON_TOWER_RANGE,
                        Constants.CANNON_NAME,
                        AttackBehaviours.createInstance(
                                Constants.attackBehaviour.CANNON
                        ),
                        clock,
                        enemyManager,
                        Constants.projectileType.CANNON_BLAST
                );
                break;
            }
        }
        if (clock != null) {
            clockManager.addClock( clock );
        }
        if (tower != null) {
            while (tower.getLevel() < formerLevel) {
                tower.upgrade();
            }
        }
        return tower;
    }

    public static Tower createInstance(int xCoord, int yCoord, SpriteSheet towerSprite, Constants.towerType towerType) {
        Tower tower = null;
        Clock clock = null;

        switch (towerType) {
            case IN_GAME_CRANE_TOWER: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.CRANE
                );
                tower = new CraneTower(
                        xCoord,
                        yCoord,
                        towerSprite,
                        Constants.craneInner,
                        Constants.craneOuter,
                        Constants.craneText,
                        Constants.CRANE_TOWER_RANGE,
                        Constants.CRANE_NAME,
                        AttackBehaviours.createInstance(
                                Constants.attackBehaviour.CRANE
                        ),
                        clock,
                        enemyManager,
                        Constants.projectileType.CRANE_TRACKER
                );
                break;
            }
            case IN_GAME_ARCANE_TOWER: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.ARCANE
                );
                tower = new ArcaneTower(
                        xCoord,
                        yCoord,
                        towerSprite,
                        Constants.arcaneInner,
                        Constants.arcaneOuter,
                        Constants.arcaneText,
                        Constants.ARCANE_TOWER_RANGE,
                        Constants.ARCANE_NAME,
                        AttackBehaviours.createInstance(
                                Constants.attackBehaviour.ARCANE
                        ),
                        clock,
                        enemyManager,
                        Constants.projectileType.ARCANE_BLAST
                );
                break;
            }
            case IN_GAME_ZOMBIE_TOWER: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.ZOMBIE
                );
                tower = new ZombieTower(
                        xCoord,
                        yCoord,
                        towerSprite,
                        Constants.zombieInner,
                        Constants.zombieOuter,
                        Constants.zombieText,
                        Constants.ZOMBIE_TOWER_RANGE,
                        Constants.ZOMBIE_NAME,
                        AttackBehaviours.createInstance(
                                Constants.attackBehaviour.ZOMBIE
                        ),
                        clock,
                        enemyManager,
                        Constants.projectileType.ZOMBIE_MISSLE
                );
                break;
            }
            case IN_GAME_CANNON_TOWER: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.CANNON
                );
                tower = new CannonTower(
                        xCoord,
                        yCoord,
                        towerSprite,
                        Constants.cannonInner,
                        Constants.cannonOuter,
                        Constants.cannonText,
                        Constants.CANNON_TOWER_RANGE,
                        Constants.CANNON_NAME,
                        AttackBehaviours.createInstance(
                                Constants.attackBehaviour.CANNON
                        ),
                        clock,
                        enemyManager,
                        Constants.projectileType.CANNON_BLAST
                );
                break;
            }
        }
        if (clock != null)
            clockManager.addClock( clock );
        return tower;
    }
}

