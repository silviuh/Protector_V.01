package ecs_container.Actors;

import java.awt.*;
import java.util.Random;
import java.util.logging.Level;

public abstract class Entity {
    protected int x, y;
    protected boolean isActive = true;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void update() {

    }

    public void render(Graphics graphicsContext) {

    }

    public void setInactive() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive() {
        isActive = true;
    }
}
