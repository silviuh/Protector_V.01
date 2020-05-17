package graphic_context;

import Constants.Constants;

import java.awt.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MouseIcon {
    private int x, y;
    private        boolean       mayUpdate;
    private        SpriteSheet   mouseSprite;
    private static ReentrantLock singletonLock;
    private static MouseIcon     mouseIcon;


    private MouseIcon(Image currentMouseImage) {
        this.mouseSprite = SpriteFactory.createSprite( currentMouseImage );
        this.mayUpdate = false;
    }

    public static MouseIcon generateMouseIcon() {
        singletonLock = new ReentrantLock();
        if (mouseIcon == null) {
            try {
                singletonLock.lock();
                mouseIcon = new MouseIcon( null );
            } finally {
                singletonLock.unlock();
            }
        }
        return mouseIcon;
    }

    public SpriteSheet getMouseSprite() {
        return mouseSprite;
    }

    public void setMouseSprite(Image currentMouseImage) {
        this.mouseSprite = SpriteFactory.createSprite( currentMouseImage );
    }

    public void update(int mouseX, int mouseY) {
        this.x = mouseX;
        this.y = mouseY;
    }

    // ar trebui schimbate la update coordonatele din sprite
    public void render(Graphics graphics, int mouseX, int mouseY) {
        if (mayUpdate) {
            if (this.mouseSprite != null) {
                graphics.drawImage(
                        mouseSprite.getImage(),
                        mouseX,
                        mouseY,
                        Constants.MOUSE_ICON_WIDTH,
                        Constants.MOUSE_ICON_HEIGHT,
                        null
                );
            }
        }
    }
}
