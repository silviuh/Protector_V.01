package game_managers.logicManagers;

import Constants.Constants;
import ecs_container.Actors.Player;
import ecs_container.Actors.enemies.Enemy;
import factories.EnemyFactory;
import graphic_context.MapManager;
import graphic_context.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * used to manage enemies
 */
public class EnemyManager {
    private                 MapManager                            mapManager;
    private static volatile EnemyManager                          enemyManager;
    private static          ReentrantLock                         singletonLock;
    private                 HashMap< String, ArrayList< Enemy > > container;

    private EnemyManager(MapManager mapManager) {
        container = new HashMap< String, ArrayList< Enemy > >();
        this.mapManager = mapManager;
    }

    public static EnemyManager getInstance(MapManager mapManager) {
        singletonLock = new ReentrantLock();
        if (enemyManager == null) {
            try {
                singletonLock.lock();
                enemyManager = new EnemyManager(
                        mapManager
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return enemyManager;
    }


    public void addEnemy(Enemy enemy, String enemyName) {
        container.computeIfAbsent( enemyName, k -> new ArrayList< Enemy >() );
        container.get( enemyName ).add( enemy );
    }

    public void render(Graphics graphics) {
        for (ArrayList< Enemy > enemyList : container.values()) {
            for (Enemy enemy : enemyList) {
                if (enemy.isActive()) {
                    enemy.render( graphics );
                }
            }
        }
    }

    /**
     * responsible for updating the enemies and manage their life time.
     */
    public void update() {
        for (ArrayList< Enemy > enemyList : container.values()) {
            for (Enemy enemy : enemyList) {
                if (enemy.isActive()) {
                    if (enemy.gotHit()) {
                        mapManager.setSpriteIsOverriden(
                                enemy.getX(),
                                enemy.getY()
                        );
                        enemy.setEnemyGotHit( false );
                    }

                    Constants.PairOfCoordinates nextPosition = calculateNextValidPosition( enemy );
                    enemy.update( nextPosition.getxCoord(), nextPosition.getyCoord() );

                    if (enemyReachedAllyKeep( enemy )) {
                        enemy.setInactive();
                        Player.modifyLives( enemy.getLivesTakenFromPlayer() );
                    }
                } else {
                    enemy = null;
                }
            }
        }
    }

    public boolean enemyReachedAllyKeep(Enemy enemy) {
        return enemy.getX() == Player.getFortressX() && enemy.getY() == Player.getFortressY();
    }

    /**
     * used for each enemy. it calculates the next valid position in the map
     * <p>the enemy may move only if the candidate tile is TRAVERSABLE and does not represent a former tile from the path.</p>
     *
     * @param enemy forEach enemy
     * @return
     */
    public Constants.PairOfCoordinates calculateNextValidPosition(Enemy enemy) {
        ArrayList< Constants.PairOfCoordinates > validCandidates = new ArrayList< Constants.PairOfCoordinates >();

        Constants.PairOfCoordinates currentPosistion = new Constants.PairOfCoordinates(
                enemy.getX(),
                enemy.getY()
        );

        for (int i = 0; i < Constants.NR_OF_DIRECTIONS; i++) {
            Constants.PairOfCoordinates candidatePosition = new Constants.PairOfCoordinates(
                    currentPosistion.getxCoord() + Constants.TILE_SIZE * Constants.directions.get( i ).getxCoord(),
                    currentPosistion.getyCoord() + Constants.TILE_SIZE * Constants.directions.get( i ).getyCoord()
            );

            if (mapManager.positionInBounds( candidatePosition ) &&
                    mapManager.getTileProperty( candidatePosition.getxCoord(), candidatePosition.getyCoord() ) == Constants.tileProperty.TRAVERSABLE &&
                    !isFormerPosition( enemy, candidatePosition )
            ) {
                validCandidates.add( candidatePosition );
            }
        }

        if (validCandidates.size() > 0) {
            Constants.PairOfCoordinates theChosenOne = validCandidates.get( new Random().nextInt( validCandidates.size() ) );
            return theChosenOne;
        }

        return new Constants.PairOfCoordinates( -1, -1 );
    }

    public boolean isFormerPosition(Enemy enemy, Constants.PairOfCoordinates candidatePair) {
        return ( candidatePair.getxCoord() == enemy.getLastX() &&
                candidatePair.getyCoord() == enemy.getLastY() );
    }

    /**
     * used to provide a list of in-range enemies for a given tower
     *
     * @param centreX tower radius circle center X coordinate
     * @param centreY tower radius circle center Y coordinate
     * @param radius  tower radius
     * @return
     */
    public ArrayList< Enemy > provideEnemiesInRange(int centreX, int centreY, int radius) {
        ArrayList< Enemy > validCandidates = new ArrayList< Enemy >();

        for (ArrayList< Enemy > enemyList : container.values()) {
            for (Enemy enemy : enemyList) {
                if (enemy.isActive()) {
                    ArrayList< Constants.PairOfCoordinates > rectangleVertices = spriteRectangleVertices( enemy.getX(), enemy.getY() );

                    for (Constants.PairOfCoordinates pair : rectangleVertices) {
                        double distance = ( pair.getxCoord() - centreX ) * ( pair.getxCoord() - centreX ) +
                                ( pair.getyCoord() - centreY ) * ( pair.getyCoord() - centreY );
                        if (distance < radius * radius) {
                            validCandidates.add( enemy );
                            break;
                        }
                    }
                }
            }
        }
        return validCandidates;
    }

    /**
     * method used to serialize enemy objects and store them into a local db.
     * @return returns the serialized result in a string form.
     */
    public String serializeEnemies() {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );

        StringBuilder result = new StringBuilder();

        for (ArrayList< Enemy > enemyList : container.values()) {
            for (Enemy enemy : enemyList) {
                if (enemy.isActive()) {
                    result.append(
                            "|" +
                                    ( enemy.getClass().getSimpleName() ) + " " +
                                    enemy.getHealth() + " " +
                                    Integer.toString( enemy.getX() ) + " " +
                                    Integer.toString( enemy.getY() )
                    );
                }
            }
        }
        return result.toString();
    }

    /**
     * used to deserialize the string and to create enemies
     * @param serializedContainer the string used for serialization
     */
    public void deserializeEnemies(String serializedContainer) {
        Constants.gameLogger.log( new Exception().getStackTrace()[1].getClassName() +
                "." +
                new Exception().getStackTrace()[1].getMethodName() +
                "()!"
        );

        String[] enemiesTokens = serializedContainer.split( "\\|" );

        for (String enemiesToken : enemiesTokens) {
            if (enemiesToken.length() > 1) {
                String[] values               = enemiesToken.split( " " );
                Tile     formerEnemyStartTile = mapManager.getTileByCoordinates( Integer.parseInt( values[2] ), Integer.parseInt( values[3] ) );
                if (formerEnemyStartTile != null) {
                    addEnemy(
                            EnemyFactory.createDeserializedInstance(
                                    formerEnemyStartTile,
                                    Constants.enemyType.values()[getEnemyByClassName( values[0] )],
                                    Double.parseDouble( values[1] )
                            ),
                            "GENERIC_NAME"
                    );
                }
            }
        }
    }


    public Integer getEnemyByClassName(String className) {
        switch (className) {
            case "Devil": {
                return 0;
            }
            case "Groot": {
                return 1;
            }
            case "Sonic": {
                return 2;
            }
            case "Owl": {
                return 3;
            }
            case "Slime": {
                return 4;
            }
        }
        return -1;
    }

    /**
     * provides the sprite rectangle represented by each enemy
     * @param xCoord enemy X
     * @param yCoord enemy Y
     * @return returns a list with the rectangle's vertices
     */
    static public ArrayList< Constants.PairOfCoordinates > spriteRectangleVertices(int xCoord, int yCoord) {
        ArrayList< Constants.PairOfCoordinates > results = new ArrayList< Constants.PairOfCoordinates >( 4 );
        results.add( // NW
                new Constants.PairOfCoordinates(
                        xCoord,
                        yCoord
                )
        );
        results.add( // NE
                new Constants.PairOfCoordinates(
                        xCoord + Constants.TILE_SIZE,
                        yCoord
                )
        );
        results.add( // SE
                new Constants.PairOfCoordinates(
                        xCoord + Constants.TILE_SIZE,
                        yCoord + Constants.TILE_SIZE
                )
        );
        results.add( // SW
                new Constants.PairOfCoordinates(
                        xCoord,
                        yCoord + Constants.TILE_SIZE
                )
        );

        return results;
    }

    public int numberOfActiveEnemies() {
        int nrOfActiveEnemies = 0;
        for (ArrayList< Enemy > enemyList : container.values()) {
            for (Enemy enemy : enemyList) {
                if (enemy.isActive())
                    nrOfActiveEnemies++;
            }
        }
        return nrOfActiveEnemies;
    }

    public void deleteContainer() {
        this.container.clear();
    }
}
