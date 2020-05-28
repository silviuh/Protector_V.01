package UI_components;

import Constants.Constants;
import factories.ImageFactory;
import graphic_context.SpriteSheet;

import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Used to manage and display the UI Towers.
 */
public class UItowersManager {
    private static          ReentrantLock   singletonLock;
    private static volatile UItowersManager uItowersManager;
    public                  UItower[]       towers;
    public                  Image           woodBackground;

    private UItowersManager(int currentLevel) {
        towers = new UItower[Constants.NR_OF_UI_TOWERS];
        woodBackground = ImageFactory.createImage(
                Constants.Image.getImageByLabel( "WOOD_BG" )
        ).getImage();


        if (currentLevel == 1) {
            towers[0] = new UItower();
            towers[0].setSprite( Constants.Image.getImageByLabel( "ARCANE_TOWER" ) );
            towers[0].setTowerPrice( 10 );
            towers[0].setHeight( Constants.TOWER_HEIGHT );
            towers[0].setWidth( Constants.TOWER_WIDTH );
            towers[0].setX( Constants.X_UI_TOWER_COORDINATE );
            towers[0].setY( Constants.Y_AXIS_TOWER_PADDING * 0 );
            towers[0].setType( Constants.towerType.values()[0] );
            towers[0].setTowerName( "ARCANE_TOWER" );

            towers[1] = new UItower();
            towers[1].setSprite( Constants.Image.getImageByLabel( "CANNON_TOWER" ) );
            towers[1].setTowerPrice( 20 );
            towers[1].setHeight( Constants.TOWER_HEIGHT );
            towers[1].setWidth( Constants.TOWER_WIDTH );
            towers[1].setX( Constants.X_UI_TOWER_COORDINATE );
            towers[1].setY( Constants.Y_AXIS_TOWER_PADDING * 1 );
            towers[1].setType( Constants.towerType.values()[1] );
            towers[1].setTowerName( "CANNON_TOWER" );

            towers[2] = new UItower();
            towers[2].setSprite( Constants.Image.getImageByLabel( "ZOMBIE_TOWER" ) );
            towers[2].setTowerPrice( 10 );
            towers[2].setHeight( Constants.TOWER_HEIGHT );
            towers[2].setWidth( Constants.TOWER_WIDTH );
            towers[2].setX( Constants.X_UI_TOWER_COORDINATE );
            towers[2].setY( Constants.Y_AXIS_TOWER_PADDING * 2 );
            towers[2].setType( Constants.towerType.values()[2] );
            towers[2].setTowerName( "ZOMBIE_TOWER" );

            towers[3] = new UItower();
            towers[3].setSprite( Constants.Image.getImageByLabel( "CRANE_TOWER" ) );
            towers[3].setTowerPrice( 20 );
            towers[3].setHeight( Constants.TOWER_HEIGHT );
            towers[3].setWidth( Constants.TOWER_WIDTH );
            towers[3].setX( Constants.X_UI_TOWER_COORDINATE );
            towers[3].setY( Constants.Y_AXIS_TOWER_PADDING * 3 );
            towers[3].setType( Constants.towerType.values()[3] );
            towers[3].setTowerName( "CRANE_TOWER" );
        }
    }

    public static UItowersManager getInstance(int currentLevel) {
        singletonLock = new ReentrantLock();
        if (uItowersManager == null) {
            try {
                singletonLock.lock();
                uItowersManager = new UItowersManager(
                        currentLevel
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return uItowersManager;
    }

    /**
     * returns a tower data when the player right-clicks the tower sprite from the towers menu
     * @param mouseX
     * @param mouseY
     * @return
     */
    public UItower getTower(int mouseX, int mouseY) {
        if (mouseX >= Constants.X_UI_TOWER_COORDINATE
                && mouseX <= Constants.X_UI_TOWER_COORDINATE + Constants.TOWER_WIDTH) {
            if (mouseY >= Constants.TOWER_HEIGHT * 0 && mouseY <= Constants.TOWER_HEIGHT)
                return this.towers[0];
            else if (mouseY >= Constants.TOWER_HEIGHT * 1 && mouseY <= Constants.TOWER_HEIGHT * 2)
                return this.towers[1];
            else if (mouseY >= Constants.TOWER_HEIGHT * 2 && mouseY <= Constants.TOWER_HEIGHT * 3)
                return this.towers[2];
            else if (mouseY >= Constants.TOWER_HEIGHT * 3 && mouseY <= Constants.TOWER_HEIGHT * 4)
                return this.towers[3];
        }

        UIConsole.addMessage( "Mouse is not set on any tower icon" );
        return null;
    }


    public void render(Graphics g) {
        for (int i = 0; i < this.towers.length; i++) {
            SpriteSheet currentSprite = this.towers[i].getSprite();

            g.drawImage(
                    woodBackground,
                    currentSprite.getX(),
                    currentSprite.getY(),
                    Constants.UI_WOOD_WIDTH,
                    Constants.UI_WOOD_HEIGHT,
                    null
            );

            g.drawImage(
                    currentSprite.getImage(),
                    currentSprite.getX(),
                    currentSprite.getY(),
                    currentSprite.getWidth(),
                    currentSprite.getHeight(),
                    null
            );
        }
    }
}
