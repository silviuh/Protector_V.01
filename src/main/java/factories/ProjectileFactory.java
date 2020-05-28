package factories;

import Constants.Constants;
import ecs_container.Actors.enemies.Enemy;
import ecs_container.Actors.projectiles.*;
import game_managers.logicManagers.ClockManager;
import graphic_context.SpriteFactory;
import utilities.Clock;

import javax.swing.*;

/**
 * Factory class used for creating different types of projectiles.
 */
public class ProjectileFactory {
    private static ClockManager clockManager;
    public static void setClockManager(ClockManager clockManager) {
        ProjectileFactory.clockManager = clockManager;
    }
    private ProjectileFactory() {


    }

    public static Projectile createInstance(Constants.projectileType projectileType, double x, double y,
                                            Enemy enemy, int towerDamage, int radius, Constants.PairOfCoordinates towerCentre) {
        Projectile projectile = null;
        Clock      clock      = null;

        switch (projectileType) {
            case CANNON_BLAST: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.PROJECTILE_TYPE_CANNON
                );
                projectile = new CannonBlast(
                        x,
                        y,
                        enemy,
                        towerDamage,
                        SpriteFactory.createSprite(
                                new ImageIcon( Constants.PROJECTILE_TYPE_CANNON_URL ).getImage()
                        ),
                        clock,
                        Constants.PROJECTILE_SPEED,
                        Constants.PROJECTILE_SIZE,
                        Constants.PROJECTILE_SIZE,
                        radius,
                        towerCentre
                );
                break;
            }

            case ZOMBIE_MISSLE: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.PROJECTILE_TYPE_ZOMBIE
                );
                projectile = new ZombieMissle(
                        x,
                        y,
                        enemy,
                        towerDamage,
                        SpriteFactory.createSprite(
                                new ImageIcon( Constants.PROJECTILE_TYPE_ZOMBIE_URL ).getImage()
                        ),
                        clock,
                        Constants.PROJECTILE_SPEED,
                        Constants.PROJECTILE_SIZE,
                        Constants.PROJECTILE_SIZE,
                        radius,
                        towerCentre
                );
                break;
            }

            case ARCANE_BLAST: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.PROJECTILE_TYPE_ARCANE
                );
                projectile = new ArcaneBlast(
                        x,
                        y,
                        enemy,
                        towerDamage,
                        SpriteFactory.createSprite(
                                new ImageIcon( Constants.PROJECTILE_TYPE_ARCANE_URL ).getImage()
                        ),
                        clock,
                        Constants.PROJECTILE_SPEED,
                        Constants.PROJECTILE_SIZE,
                        Constants.PROJECTILE_SIZE,
                        radius,
                        towerCentre
                );
                break;
            }

            case CRANE_TRACKER: {
                clock = ClockFactory.createInstance(
                        Constants.clockType.PROJECTILE_TYPE_CRANE
                );
                projectile = new CraneTracker(
                        x,
                        y,
                        enemy,
                        towerDamage,
                        SpriteFactory.createSprite(
                                new ImageIcon( Constants.PROJECTILE_TYPE_CRANE_URL ).getImage()
                        ),
                        clock,
                        Constants.PROJECTILE_SPEED,
                        Constants.PROJECTILE_SIZE,
                        Constants.PROJECTILE_SIZE,
                        radius,
                        towerCentre
                );
                break;
            }
        }

        clockManager.addClock( clock );
        return projectile;
    }
}
