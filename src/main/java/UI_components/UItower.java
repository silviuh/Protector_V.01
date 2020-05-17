package UI_components;

import Constants.Constants;
import factories.ImageFactory;
import graphic_context.SpriteFactory;
import graphic_context.SpriteSheet;

public class UItower {
    private float               towerPrice;
    private SpriteSheet         sprite;
    private Constants.towerType type;

    public String getTowerName() {
        return towerName;
    }

    public void setTowerName(String towerName) {
        this.towerName = towerName;
    }

    private String towerName;

    public SpriteSheet getSprite() {
        return sprite;
    }

    public Constants.towerType getType() {
        return type;
    }

    public void setType(Constants.towerType type) {
        this.type = type;
    }

    public void setSprite(Constants.Image image) {
        this.sprite = SpriteFactory.createSprite(
                ImageFactory.createImage(
                        image
                ).getImage()
        );
    }

    public float getTowerPrice() {
        return towerPrice;
    }

    public int getHeight() {
        return this.sprite.getHeight();
    }

    public int getY() {
        return this.sprite.getY();
    }

    public void setY(int y) {
        this.sprite.setY( y );
    }

    public int getX() {
        return this.sprite.getX();
    }

    public void setX(int x) {
        this.sprite.setX( x );
    }

    public void setHeight(int height) {
        this.sprite.setHeight( height );
    }

    public void setTowerPrice(float towerPrice) {
        this.towerPrice = towerPrice;
    }

    public int getWidth() {
        return this.sprite.getWidth();
    }

    public void setWidth(int width) {
        this.sprite.setWidth( width );
    }
}
