package ecs_container.Actors.enemies;

import ecs_container.Actors.Entity;
import graphic_context.SpriteSheet;

import java.awt.*;
import java.util.ArrayList;

public abstract class Mob extends Entity {

    protected ArrayList< SpriteSheet > sprites;
    protected int                      currentSpriteNumber;
    protected float                    speed;
    protected boolean                  isMoving = false;
    int height;
    int width;

    public Mob() {
        super();
    }

    public void move() {

    }

    public Mob(int x, int y, int height, int width, ArrayList< SpriteSheet > sprites) {
        super( x, y );
        this.sprites = sprites;
        this.height = height;
        this.width = width;
        this.currentSpriteNumber = 0;
    }

    public Mob(int x, int y, ArrayList< SpriteSheet > sprites) {
        super( x, y );
        this.sprites = sprites;
        this.currentSpriteNumber = 0;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphicsContext) {

    }

    private boolean collision() {
        return false;
    }

    public void setInactive() {
        super.setInactive();
    }
}
