package ecs_container.Actors.projectiles;

import Constants.Constants;
import ecs_container.Actors.Entity;
import ecs_container.Actors.enemies.Enemy;
import graphic_context.SpriteSheet;
import utilities.Clock;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.function.Consumer;

public class Projectile extends Entity {
    protected int         towerRadius;
    protected double      xCoord; // for smoothness
    protected double      yCoord;
    protected int         height;
    protected int         width;
    protected Enemy       target;
    protected double      damage;
    protected double      direction;
    protected double      speed;
    protected SpriteSheet sprite;
    protected Clock       clock;

    public Projectile(double x, double y, Enemy targetEnemy, int towerDamage,
                      SpriteSheet sprite, Clock clock, double speed,
                      int height, int width, int radius) {
        super( ( int ) x, ( int ) y );
        this.target = targetEnemy;
        this.damage = towerDamage;
        this.sprite = sprite;
        this.clock = clock;
        this.speed = speed;
        this.height = height;
        this.width = width;
        this.towerRadius = radius;

        xCoord = x;
        yCoord = y;
        this.damage = 20;
    }

    protected void updateDirection() {
        this.direction = Math.atan2(
                this.target.getY() - this.yCoord,
                this.target.getX() - this.xCoord
        );

    }

    public void update() {
        if (clock.mayUpate() && this.isActive()) {
            updateDirection();
            this.xCoord += speed * Math.cos( direction );
            this.yCoord += speed * Math.sin( direction );

            if (colides( target.getX(), target.getY() )) {
                target.takeDamage( this.damage );
                target.setEnemyGotHit(true);
                this.setInactive();
            }
        }
        // doing damage and eventually set inactive
    }

    // public boolean collides

    public void render(Graphics g) {
        if (isActive()) {
            g.drawImage(
                    this.sprite.getImage(),
                    ( int ) this.xCoord,
                    ( int ) this.yCoord,
                    this.width,
                    this.height,
                    null
            );
        }
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public boolean colides(int destX, int destY) {
        Constants.PairOfCoordinates leftTopCorner     = new Constants.PairOfCoordinates( ( int ) this.xCoord, ( int ) this.yCoord );
        Constants.PairOfCoordinates rightTopCorner    = new Constants.PairOfCoordinates( ( int ) this.xCoord + Constants.PROJECTILE_SIZE, ( int ) this.yCoord );
        Constants.PairOfCoordinates leftBottomCorner  = new Constants.PairOfCoordinates( ( int ) this.xCoord, ( int ) this.yCoord + Constants.PROJECTILE_SIZE );
        Constants.PairOfCoordinates rightBottomCorner = new Constants.PairOfCoordinates( ( int ) this.xCoord + Constants.PROJECTILE_SIZE, ( int ) this.yCoord + Constants.PROJECTILE_SIZE );

        return (
                ( leftTopCorner.getxCoord() >= destX && leftTopCorner.getxCoord() <= destX + Constants.TILE_SIZE && leftTopCorner.getyCoord() >= destY && leftTopCorner.getyCoord() <= destY + Constants.TILE_SIZE ) ||
                        ( rightTopCorner.getxCoord() >= destX && rightTopCorner.getxCoord() <= destX + Constants.TILE_SIZE && rightTopCorner.getyCoord() >= destY && rightTopCorner.getyCoord() <= destY + Constants.TILE_SIZE ) ||
                        ( leftBottomCorner.getxCoord() >= destX && leftBottomCorner.getxCoord() <= destX + Constants.TILE_SIZE && leftBottomCorner.getyCoord() >= destY && leftBottomCorner.getyCoord() <= destY + Constants.TILE_SIZE ) ||
                        ( rightBottomCorner.getxCoord() >= destX && rightBottomCorner.getxCoord() <= destX + Constants.TILE_SIZE && rightBottomCorner.getyCoord() >= destY && rightBottomCorner.getyCoord() <= destY + Constants.TILE_SIZE )
        );
    }
}
