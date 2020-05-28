package graphic_context;

import Constants.Constants;
import factories.ImageFactory;
import game_managers.db.DBManager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class MapManager {
    private                 DBManager                   dbManager;
    private                 Constants.PairOfCoordinates allyKeepCoordinates = null;
    private                 int                         enemyStartingPoint  = -1;
    private static volatile MapManager                  mapManager;
    private static          ReentrantLock               singletonLock;
    private                 int                         SIZE;
    private                 List< Tile >                tiles;
    private                 JPanel                      mainFrameObserver;
    private                 int                         tileSize;

    /**
     * loads the current level map from the db
     *
     * @param currentLevel
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void loadMapFromDB(int currentLevel) throws SQLException, ClassNotFoundException {
        int currentTile = 0;
        dbManager.openConnection();
        String mapContent = dbManager.SELECTFileContent( currentLevel );

        String[] charTileList = mapContent.split( "\\s+" );
        for (String tileString : charTileList) {
            Constants.Image requiredImage = (
                    Constants.Image.values()[
                            Integer.parseInt( tileString )
                            ]
            );

            if (Integer.parseInt( tileString ) == Constants.ALLY_KEEP_TILE_CODE) {
                allyKeepCoordinates = new Constants.PairOfCoordinates(
                        Constants.TILE_SIZE * ( currentTile / Constants.NR_OF_TILES_PER_WIDTH ),
                        Constants.TILE_SIZE * ( currentTile % Constants.NR_OF_TILES_PER_WIDTH )
                );
            }

            if (Integer.parseInt( tileString ) == Constants.ENEMY_KEEP_TILE_CODE) {
                enemyStartingPoint = currentTile;
            }

            this.tiles.add(
                    TileFactory.createTile(
                            SpriteFactory.createSprite(
                                    ImageFactory.createImage( requiredImage ).getImage()
                            ),
                            requiredImage.property
                    )
            );
            currentTile++;
        }
        setTileCoordinates();
    }

    public void deleteContainer() {
        this.tiles.clear();
    }

    public class Pair {
        private int xCoord;
        private int yCoord;

        public Pair(int xCoord, int yCoord) {
            this.xCoord = xCoord;
            this.yCoord = yCoord;
        }

        public void setCoord(int xCoord, int yCoord) {
            this.xCoord = xCoord;
            this.yCoord = yCoord;
        }

        public int getxCoord() {
            return xCoord;
        }

        public int getyCoord() {
            return yCoord;
        }

        public void setyCoord(int yCoord) {
            this.yCoord = yCoord;
        }
    }

    public List< Tile > getTiles() {
        return tiles;
    }

    public void setTiles(List< Tile > tiles) {
        this.tiles = tiles;
    }

    private MapManager(int SIZE, int tileSize, DBManager dbManager) {
        this.SIZE = SIZE;
        tiles = new ArrayList< Tile >( SIZE * SIZE );
        this.tileSize = tileSize;
        this.dbManager = dbManager;
    }

    public void loadMapFromTextFile(String levelFilePath) {
        try {
            loadConfigFromTextFile( levelFilePath );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        setTileCoordinates();
    }

    public static MapManager getInstance(DBManager dbManager) {
        singletonLock = new ReentrantLock();
        if (mapManager == null) {
            try {
                singletonLock.lock();
                mapManager = new MapManager(
                        Constants.MAP_SIZE,
                        Constants.TILE_SIZE,
                        dbManager
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return mapManager;
    }

    /**
     * loads map directly from the text file
     *
     * @param txtFilePath
     * @throws IOException
     */
    void loadConfigFromTextFile(String txtFilePath) throws IOException {
        int currentTile = 0;
        BufferedReader bufReader = new BufferedReader(
                new FileReader( txtFilePath )
        );
        String line = bufReader.readLine();

        while (line != null) {
            List< String > lineList = Arrays.asList( line.split( " " ) );
            for (String tileString : lineList) {
                Constants.Image requiredImage = (
                        Constants.Image.values()[
                                Integer.parseInt( tileString )
                                ]
                );

                if (Integer.parseInt( tileString ) == Constants.ALLY_KEEP_TILE_CODE) {
                    allyKeepCoordinates = new Constants.PairOfCoordinates(
                            Constants.TILE_SIZE * ( currentTile / Constants.NR_OF_TILES_PER_WIDTH ),
                            Constants.TILE_SIZE * ( currentTile % Constants.NR_OF_TILES_PER_WIDTH )
                    );
                }

                if (Integer.parseInt( tileString ) == Constants.ENEMY_KEEP_TILE_CODE) {
                    enemyStartingPoint = currentTile;
                }

                this.tiles.add(
                        TileFactory.createTile(
                                SpriteFactory.createSprite(
                                        ImageFactory.createImage( requiredImage ).getImage()
                                ),
                                requiredImage.property
                        )
                );

                currentTile++;
            }
            line = bufReader.readLine();
        }

        bufReader.close();
    }

    void createSampleMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tiles.add(
                        TileFactory.createTile(
                                SpriteFactory.createSprite(
                                        ImageFactory.createImage(
                                                Constants.Image.SOLID_WALL
                                        ).getImage()
                                ), Constants.tileProperty.BASIC
                        )
                );
            }
        }
    }

    public void render(Graphics graphicsContext) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tiles.get( i * SIZE + j ).render( graphicsContext );

                if (tiles.get( i * SIZE + j ).spriteIsOverriden) {
                    graphicsContext.drawImage(
                            new ImageIcon( Constants.BLOOD_SPILL_4_URL ).getImage(), // in case enemies get hit
                            tiles.get( i * SIZE + j ).getX(),
                            tiles.get( i * SIZE + j ).getY(),
                            Constants.TILE_SIZE,
                            Constants.TILE_SIZE,
                            null
                    );
                }
            }
        }
    }

    public void setTileCoordinates() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tiles.get( i * SIZE + j ).
                        sprite.setHeight( Constants.TILE_SIZE
                );

                tiles.get( i * SIZE + j ).
                        sprite.setWidth( Constants.TILE_SIZE
                );

                tiles.get( i * SIZE + j ).
                        sprite.setX(
                        j * Constants.TILE_SIZE
                );

                tiles.get( i * SIZE + j ).
                        sprite.setY(
                        i * Constants.TILE_SIZE
                );
            }
        }
    }

    /**
     * method used to try to place a tower to a given position
     * @param desiredX tower X
     * @param desiredY tower Y
     * @param towerImage tower Sprite
     * @return null if the position is invalid
     */
    public Pair placeTower(int desiredX, int desiredY, Image towerImage) {
        int  i          = 0;
        Pair returnPair = new Pair( -1, -1 );
        for (Iterator< Tile > it = tiles.iterator(); it.hasNext(); i++) {

            Tile tile  = it.next();
            int  tileX = tile.sprite.getX();
            int  tileY = tile.sprite.getY();
            returnPair.setCoord( tileX, tileY );

            if (
                    tileX <= desiredX &&
                            desiredX <= ( tileX + Constants.TILE_SIZE ) &&
                            tileY <= desiredY &&
                            desiredY <= ( tileY + Constants.TILE_SIZE )
            ) {
                return returnPair;
            }
        }
        return null;
    }

    public Constants.tileProperty getTileProperty(int xCoord, int yCoord) {
        Constants.tileProperty property = Constants.tileProperty.UNINITIALIZED;
        for (Tile tile : tiles) {
            if (tile.getX() == xCoord && tile.getY() == yCoord) {
                property = tile.getTileProperty();
                return property;
            }
        }
        return property;
    }

    public Tile getTileByCoordinates(int xCoord, int yCoord) {
        for (Tile tile : tiles) {
            if (tile.getX() == xCoord && tile.getY() == yCoord) {
                return tile;
            }
        }
        return null;
    }

    public boolean positionInBounds(Constants.PairOfCoordinates pair) {
        return ( pair.getxCoord() / Constants.TILE_SIZE >= 0 &&
                pair.getxCoord() / Constants.TILE_SIZE <= Constants.MAP_SIZE &&
                pair.getyCoord() / Constants.TILE_SIZE >= 0 &&
                pair.getyCoord() / Constants.TILE_SIZE <= Constants.MAP_SIZE
        );
    }

    public void setSpriteIsOverriden(int xCoord, int yCoord) {
        for (Tile tile : tiles) {
            if (tile.getX() == xCoord && tile.getY() == yCoord) {
                tile.setSpriteIsOverriden( true );
            }
        }
    }

    public Tile getTile(int index) {
        return this.tiles.get( index );
    }

    public Constants.PairOfCoordinates getAllyKeepCoordinates() {
        return allyKeepCoordinates;
    }

    public int getEnemyStartingPoint() {
        return enemyStartingPoint;
    }
}

