package graphic_context;

import Constants.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * container for java.awt Image
 */
public class SpriteSheet {
    private Image                  image;
    private Constants.tileProperty property;
    private int                    width;
    private int                    height;
    private JPanel                 mainFrameObserver;

    protected int x;
    protected int y;

    public JPanel getMainFrameObserver() {
        return mainFrameObserver;
    }

    public Constants.tileProperty getProperty() {
        return property;
    }

    public void setMainFrameObserver(JPanel mainFrameObserver) {
        this.mainFrameObserver = mainFrameObserver;
    }

    public SpriteSheet(Image image) {
        this.property = Constants.tileProperty.BASIC;
        this.image = image;
    }

    public SpriteSheet(Constants.tileProperty property, Image image, int width, int height) {
        this.property = property;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return this.image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isSolid() {
        return ( property == Constants.tileProperty.SOLID );
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setProperty(Constants.tileProperty property) {
        this.property = property;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public SpriteSheet getResizedVersion(int width, int height) {
        SpriteSheet temp = new SpriteSheet(
                this.image
        );
        temp.setWidth( width );
        temp.setHeight( height );
        return temp;
    }
}
