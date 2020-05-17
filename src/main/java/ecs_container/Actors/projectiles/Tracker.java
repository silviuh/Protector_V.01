package ecs_container.Actors.projectiles;

import Constants.Constants;
import ecs_container.Actors.enemies.Enemy;
import graphic_context.SpriteSheet;
import utilities.Clock;

public class Tracker extends Projectile {
    public Constants.PairOfCoordinates towerCentre;
    public int                         dx;
    public int                         dy;

    public Tracker(double x, double y, Enemy targetEnemy, int towerDamage,
                   SpriteSheet sprite, Clock clock, double speed,
                   int height, int width, int radius, Constants.PairOfCoordinates towerCentre) {
        super(
                x,
                y,
                targetEnemy,
                towerDamage,
                sprite,
                clock,
                speed,
                height,
                width,
                radius
        );

        xCoord = x;
        yCoord = y;

        this.towerCentre = towerCentre;
        this.dx = 0;
        this.dy = 0;
        setDirection();
    }

    @Override
    public void update() {
        if (
                this.xCoord + speed * dx + Constants.PROJECTILE_SIZE <= Constants.TILE_SIZE ||
                        this.xCoord + speed * dx + Constants.PROJECTILE_SIZE >= Constants.BOARD_WIDTH ||
                        this.yCoord + speed * dy + Constants.PROJECTILE_SIZE >= Constants.BOARD_HEIGHT ||
                        this.yCoord + speed * dy + Constants.PROJECTILE_SIZE <= Constants.TILE_SIZE ||
                        !inRange()
        ) {
            this.setInactive();
        }
        if (clock.mayUpate() && this.isActive()) {
            this.setDirection();
            this.xCoord += speed * dx;
            this.yCoord += speed * dy;

            if (super.colides( target.getX(), target.getY() )) {
                this.setInactive();
                target.setEnemyGotHit(true);
                target.takeDamage( this.damage );
            }
        }
    }

    private void setDirection() {
        if (target.getX() <= this.xCoord && target.getY() <= this.yCoord) {
            this.dx = -1;
            this.dy = -1;
        } else if (target.getX() <= this.xCoord && target.getY() >= this.yCoord) {
            this.dx = -1;
            this.dy = 1;
        } else if (target.getX() >= this.xCoord && target.getY() <= this.yCoord) {
            this.dx = 1;
            this.dy = -1;
        } else if (target.getX() >= this.xCoord && target.getY() >= this.yCoord) {
            this.dx = 1;
            this.dy = 1;
        }
    }

    private boolean inRange() {
        double distance =
                ( xCoord + Constants.PROJECTILE_SIZE / 2 - towerCentre.getxCoord() ) * ( xCoord + Constants.PROJECTILE_SIZE / 2 - towerCentre.getxCoord() )
                        + ( yCoord + Constants.PROJECTILE_SIZE / 2 - towerCentre.getyCoord() ) * ( yCoord + Constants.PROJECTILE_SIZE / 2 - towerCentre.getyCoord() );
        return distance < this.towerRadius * this.towerRadius;

    }
}
