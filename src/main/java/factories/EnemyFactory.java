package factories;

import Constants.Constants;
import ecs_container.Actors.enemies.*;
import game_managers.logicManagers.ClockManager;
import graphic_context.Tile;
import utilities.Clock;

/**
 * Factory class used for creating attackBehaviours for different types of enemies.
 */
public class EnemyFactory {
    private static ClockManager clockManager;
    private EnemyFactory() {


    }

    public static void setClockManager(ClockManager clockManager) {
        EnemyFactory.clockManager = clockManager;
    }

    public static Enemy createDeserializedInstance(Tile startTile, Constants.enemyType type, double health) {
        Enemy enemy = null;
        switch (type) {
            case TYPE_1: {
                Clock clock = ClockFactory.createInstance( Constants.clockType.ENEMY_1 );
                enemy = new Devil(
                        startTile,
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        SpriteContainerFactory.createSprite( Constants.enemyType.TYPE_1 ),
                        clock,
                        Constants.LIVES_TAKEN_BY_DEVIL
                );
                clockManager.addClock( clock );
                break;
            }
            case TYPE_2: {
                Clock clock = ClockFactory.createInstance( Constants.clockType.ENEMY_2 );
                enemy = new Groot(
                        startTile,
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        SpriteContainerFactory.createSprite( Constants.enemyType.TYPE_2 ),
                        clock,
                        Constants.LIVES_TAKEN_BY_GROOT
                );
                clockManager.addClock( clock );
                break;

            }
            case TYPE_3: {
                Clock clock = ClockFactory.createInstance( Constants.clockType.ENEMY_3 );
                enemy = new Sonic(
                        startTile,
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        SpriteContainerFactory.createSprite( Constants.enemyType.TYPE_3 ),
                        clock,
                        Constants.LIVES_TAKEN_BY_SONIC
                );
                clockManager.addClock( clock );
                break;
            }

            case TYPE_4: {
                Clock clock = ClockFactory.createInstance( Constants.clockType.ENEMY_4 );
                enemy = new Owl(
                        startTile,
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        SpriteContainerFactory.createSprite( Constants.enemyType.TYPE_4 ),
                        clock,
                        Constants.LIVES_TAKEN_BY_OWL
                );
                clockManager.addClock( clock );
                break;
            }

            case TYPE_5: {
                Clock clock = ClockFactory.createInstance( Constants.clockType.ENEMY_5 );
                enemy = new Slime(
                        startTile,
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        SpriteContainerFactory.createSprite( Constants.enemyType.TYPE_5 ),
                        clock,
                        Constants.LIVES_TAKEN_BY_SLIME
                );
                clockManager.addClock( clock );
                break;
            }
        }
        return enemy;
    }

    public static Enemy createInstance(Tile startTile, Constants.enemyType type) {
        Enemy enemy = null;

        switch (type) {
            case TYPE_1: {
                Clock clock = ClockFactory.createInstance( Constants.clockType.ENEMY_1 );
                enemy = new Devil(
                        startTile,
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        SpriteContainerFactory.createSprite( Constants.enemyType.TYPE_1 ),
                        clock,
                        Constants.LIVES_TAKEN_BY_DEVIL
                );
                clockManager.addClock( clock );
                break;
            }
            case TYPE_2: {
                Clock clock = ClockFactory.createInstance( Constants.clockType.ENEMY_2 );
                enemy = new Groot(
                        startTile,
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        SpriteContainerFactory.createSprite( Constants.enemyType.TYPE_2 ),
                        clock,
                        Constants.LIVES_TAKEN_BY_GROOT
                );
                clockManager.addClock( clock );
                break;

            }
            case TYPE_3: {
                Clock clock = ClockFactory.createInstance( Constants.clockType.ENEMY_3 );
                enemy = new Sonic(
                        startTile,
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        SpriteContainerFactory.createSprite( Constants.enemyType.TYPE_3 ),
                        clock,
                        Constants.LIVES_TAKEN_BY_SONIC
                );
                clockManager.addClock( clock );
                break;
            }

            case TYPE_4: {
                Clock clock = ClockFactory.createInstance( Constants.clockType.ENEMY_4 );
                enemy = new Owl(
                        startTile,
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        SpriteContainerFactory.createSprite( Constants.enemyType.TYPE_4 ),
                        clock,
                        Constants.LIVES_TAKEN_BY_OWL
                );
                clockManager.addClock( clock );
                break;
            }

            case TYPE_5: {
                Clock clock = ClockFactory.createInstance( Constants.clockType.ENEMY_5 );
                enemy = new Slime(
                        startTile,
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        SpriteContainerFactory.createSprite( Constants.enemyType.TYPE_5 ),
                        clock,
                        Constants.LIVES_TAKEN_BY_SLIME
                );
                clockManager.addClock( clock );
                break;
            }
        }
        return enemy;
    }
}
