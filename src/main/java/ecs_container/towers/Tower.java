package ecs_container.towers;

import Constants.Constants;
import ecs_container.Actors.enemies.Enemy;
import ecs_container.Actors.projectiles.Projectile;
import factories.ProjectileFactory;
import game_managers.logicManagers.EnemyManager;
import graphic_context.SpriteSheet;
import utilities.Clock;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Tower {
    public    String                   name;
    public    boolean                  drawRange       = false;
    protected SpriteSheet              sprite;
    protected Clock                    clock;
    protected EnemyManager             enemyManagerRef = null;
    protected TowerBehaviour           behaviour;
    protected Constants.projectileType projectileType;

    protected ArrayList< Projectile > projectiles;

    boolean isActive;
    public    int                 range;
    protected int                 damage;
    protected int                 xPos;
    protected int                 yPos;
    protected Color               innerCirclecColor = null;
    protected Color               outerCircleColor  = null;
    protected Color               textInfoColor     = null;
    protected Constants.towerType type;

    public SpriteSheet getSprite() {
        return sprite;
    }

    public void setSprite(SpriteSheet sprite) {
        this.sprite = sprite;
    }

    public Tower(int xPos, int yPos, SpriteSheet sprite, Color innerCirclecColor,
                 Color outerCircleColor, Color textInfoColor, int range, String name,
                 TowerBehaviour towerBehaviour, Clock clock, EnemyManager enemyManager, Constants.projectileType projectileType) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.sprite = sprite;
        this.drawRange = false;
        this.innerCirclecColor = innerCirclecColor;
        this.outerCircleColor = outerCircleColor;
        this.textInfoColor = textInfoColor;
        this.range = range;
        this.name = name;
        this.damage = towerBehaviour.damage;
        this.clock = clock;
        this.enemyManagerRef = enemyManager;
        this.behaviour = towerBehaviour;
        this.projectiles = new ArrayList< Projectile >();
        this.projectileType = projectileType;

        this.setActive( true );
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setDrawRange(boolean drawRange) {
        this.drawRange = drawRange;
    }

    public boolean isDrawRange() {
        return drawRange;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void update() {
        if (clock.mayUpate()) {
            ArrayList< Enemy > enemies = enemyManagerRef.provideEnemiesInRange(
                    this.xPos + Constants.TILE_SIZE / 2,
                    this.yPos + Constants.TILE_SIZE / 2,
                    this.range - Constants.RANGE_REFINE
            );

            int numberOfEnemiesInRange = enemies.size();

            if (numberOfEnemiesInRange > 0) {
                projectiles.add(
                        ProjectileFactory.createInstance(
                                projectileType,
                                this.xPos + Constants.PROJECTILE_PADDING,
                                this.yPos + Constants.PROJECTILE_PADDING,
                                enemies.get( new Random().nextInt( numberOfEnemiesInRange ) ),
                                this.damage,
                                this.range,
                                new Constants.PairOfCoordinates(
                                        getxPos() + Constants.TILE_SIZE / 2,
                                        getyPos() + Constants.TILE_SIZE / 2
                                )
                        )
                );
            }
        }

        for (Projectile projectile : projectiles) {
            if (projectile.isActive()) {
                projectile.update();
            } else {
                projectile = null;
            }
        }
    }

    public double refund() {
        this.setActive( false );
        return this.behaviour.refundValue;
    }

    public int getLevel() {
        return this.behaviour.currentLevel;
    }

    public boolean upgrade() {
        if (this.behaviour.currentLevel < this.behaviour.maxLevel) {
            this.behaviour.currentLevel++;
            this.damage += this.behaviour.modifierIncreaseDamage;
            this.clock.modifyEntityUpdateRate( -this.behaviour.modifierDecreaseInCooldown );
            this.range += this.behaviour.modifierIncreaseInRange;
            return true;
        }
        return false;
    }

    public boolean mayUpgrade() {
        return this.behaviour.currentLevel != this.behaviour.maxLevel;
    }

    public void render(Graphics graphicsContext) {
        if (drawRange) {
            graphicsContext.setColor( this.outerCircleColor );
            graphicsContext.drawOval(
                    ( getxPos() ) + Constants.TILE_SIZE / 2 - this.range,
                    ( getyPos() ) + Constants.TILE_SIZE / 2 - this.range,
                    this.range * 2,
                    this.range * 2
            );

            graphicsContext.setColor( innerCirclecColor );
            graphicsContext.fillOval(
                    ( getxPos() ) + Constants.TILE_SIZE / 2 - this.range,
                    ( getyPos() ) + Constants.TILE_SIZE / 2 - this.range,
                    this.range * 2,
                    this.range * 2
            );

            graphicsContext.setColor( this.textInfoColor );
            graphicsContext.drawString( // N
                    "[" + this.name + "]",
                    ( getxPos() ) - 40,
                    ( getyPos() ) - 30
            );
            graphicsContext.drawString( // E
                    "[DAMAGE]: " + this.damage,
                    ( getxPos() ) + Constants.TILE_SIZE + 23,
                    ( getyPos() ) + Constants.TILE_SIZE / 2
            );
            graphicsContext.drawString( // S
                    "[UPD_RATE]: " + this.clock.getEntityUpdateRate(),
                    ( getxPos() ) - 40,
                    ( getyPos() ) + 30 + Constants.TILE_SIZE
            );
            graphicsContext.drawString( // W
                    "[RANGE]: " + Integer.toString( this.range ),
                    ( getxPos() ) - 150 - this.name.length(),
                    ( getyPos() ) + Constants.TILE_SIZE / 2
            );
        }

        graphicsContext.drawImage(
                sprite.getImage(),
                xPos,
                yPos,
                sprite.getWidth(),
                sprite.getHeight(),
                null
        );

        Graphics2D graphics2D = ( Graphics2D ) graphicsContext.create();
        for (Projectile projectile : projectiles) {
            if (projectile.isActive()) {
                graphics2D.rotate(
                        projectile.getDirection() + Math.toRadians( 90 ),
                        projectile.getxCoord() + Constants.PROJECTILE_SIZE + Constants.PROJECTILE_SIZE / 2,
                        projectile.getyCoord() + Constants.PROJECTILE_SIZE + Constants.PROJECTILE_SIZE / 2
                );

                projectile.render( graphicsContext );

                graphics2D.rotate(
                        -projectile.getDirection() + Math.toRadians( -90 ),
                        projectile.getxCoord() + Constants.PROJECTILE_SIZE + Constants.PROJECTILE_SIZE / 2,
                        projectile.getyCoord() + Constants.PROJECTILE_SIZE + Constants.PROJECTILE_SIZE / 2
                );
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public float getUpgradeCost() {
        return ( float ) this.behaviour.upgradeCost;
    }

}
