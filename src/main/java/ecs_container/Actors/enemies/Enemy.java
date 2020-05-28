package ecs_container.Actors.enemies;

import Constants.Constants;
import graphic_context.SpriteSheet;
import graphic_context.Tile;
import utilities.Clock;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * blueprint for specialized enemies
 * abstract class
 */
public abstract class Enemy extends Mob {

    protected boolean enemyGotHit = false;
    protected int     livesTaken;
    protected Tile    startTile;
    protected double  health;
    protected double  initialHealth;
    protected int     updateDelay = 0;
    protected Clock   clock;
    protected int     lastX;
    protected int     lastY;

    public Enemy() {
        super();
    }

    public Enemy(Tile startTile, int height, int width, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile.getX(),
                startTile.getY(),
                height,
                width,
                sprites
        );

        this.startTile = startTile;
        this.health = 100;
        this.initialHealth = health;
        this.isActive = true;
        this.livesTaken = livesTaken;
        this.enemyGotHit = false;
        this.clock = clock;

        this.lastX = startTile.getX();
        this.lastY = startTile.getY();
    }

    public Enemy(Tile startTile, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile.getX(),
                startTile.getY(),
                sprites
        );
        this.clock = clock;
        this.enemyGotHit = false;
        this.startTile = startTile;
        this.health = 100;
        this.isActive = true;
        this.lastX = -1;
        this.lastY = -1;
        this.livesTaken = livesTaken;
    }

    @Override
    public void render(Graphics graphicsContext) {
        if (this.isActive()) {
            double healthPercentage = this.health / this.initialHealth;

            graphicsContext.drawImage(
                    this.sprites.get( currentSpriteNumber ).getImage(),
                    x,
                    y,
                    this.sprites.get( currentSpriteNumber ).getWidth(),
                    this.sprites.get( currentSpriteNumber ).getHeight(),
                    null
            );

            graphicsContext.drawImage(
                    new ImageIcon( Constants.HEALTH_BAR_BACKGROUND_URL ).getImage(),
                    x,
                    y - Constants.ENEMY_HEALTH_BAR_WIDTH,
                    Constants.TILE_SIZE,
                    Constants.ENEMY_HEALTH_BAR_HEIGHT,
                    null
            );
            graphicsContext.drawImage(
                    new ImageIcon( Constants.HEALTH_BAR_FOREGROUND_URL ).getImage(),
                    x,
                    y - Constants.ENEMY_HEALTH_BAR_WIDTH,
                    ( int ) ( Constants.TILE_SIZE * healthPercentage ),
                    Constants.ENEMY_HEALTH_BAR_HEIGHT,
                    null
            );
            graphicsContext.drawImage(
                    new ImageIcon( Constants.HEALTH_BAR_BORDER_URL ).getImage(),
                    x,
                    y - Constants.ENEMY_HEALTH_BAR_WIDTH,
                    Constants.TILE_SIZE,
                    Constants.ENEMY_HEALTH_BAR_HEIGHT,
                    null
            );
        }
    }

    public boolean gotHit() {
        return enemyGotHit;
    }

    public void setEnemyGotHit(boolean enemyGotHit) {
        this.enemyGotHit = enemyGotHit;
    }

    @Override
    public void update() {
        if (clock.mayUpate()) {
            super.update();

            this.currentSpriteNumber++;
            if (currentSpriteNumber == this.sprites.size())
                currentSpriteNumber = 0;

            this.x += Constants.TILE_SIZE;
        }
    }

    /**
     * Updates enemy coordinates and changes the current frame, giving the impression of moving
     * @param xCoord enemy x coordinate on the board
     * @param yCoord enemy y coordinate on the board
     */
    public void update(int xCoord, int yCoord) {
        if (clock.mayUpate()) {
            super.update();
            this.lastX = this.x;
            this.lastY = this.y;
            this.x = xCoord;
            this.y = yCoord;

            this.currentSpriteNumber++;
            if (currentSpriteNumber == this.sprites.size())
                currentSpriteNumber = 0;
        }
    }

    public void takeDamage(double damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.setInactive();
        }
    }

    public double getHealth() {
        return health;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    public int getLastX() {
        return lastX;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public void setInactive() {
        super.setInactive();
    }

    public int getLivesTakenFromPlayer() {
        return livesTaken;
    }
}

