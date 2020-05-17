package graphic_context;

import Constants.Constants;

import javax.swing.*;
import java.awt.*;

public class Tile {
    public SpriteSheet sprite;
    public boolean     spriteIsOverriden = false;

    public Tile(SpriteSheet sprite, Constants.tileProperty property) {
        this.sprite = sprite;
        this.sprite.setProperty( property );
        this.spriteIsOverriden = false;
    }

    public void render(Graphics graphicsContext) {
        graphicsContext.drawImage(
                sprite.getImage(),
                sprite.getX(),
                sprite.getY(),
                sprite.getWidth(),
                sprite.getHeight(),
                null
        );
    }

    public void setSprite(ImageIcon imageIcon) {
        this.sprite.setImage( imageIcon.getImage() );
    }

    public boolean isSpriteIsOverriden() {
        return spriteIsOverriden;
    }

    public void setSpriteIsOverriden(boolean spriteIsOverriden) {
        this.spriteIsOverriden = spriteIsOverriden;
    }

    public int getX() {
        return sprite.getX();
    }

    public int getY() {
        return sprite.getY();
    }

    public Constants.tileProperty getTileProperty() {
        return this.sprite.getProperty();
    }
}
