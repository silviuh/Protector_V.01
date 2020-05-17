package ecs_container.Actors.projectiles;

import Constants.Constants;
import ecs_container.Actors.enemies.Enemy;
import graphic_context.SpriteSheet;
import utilities.Clock;

public class ZombieMissle extends Missle {

    public ZombieMissle(double x, double y, Enemy targetEnemy, int towerDamage,
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
                radius,
                towerCentre
        );
    }
}
