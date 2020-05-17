package game_managers.logicManagers;

import Constants.Constants;
import ecs_container.Actors.Player;
import ecs_container.Actors.enemies.Enemy;
import graphic_context.MapManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

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
        if (container.get( enemyName ) == null) {
            container.put( enemyName, new ArrayList< Enemy >() );
        }
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


}
