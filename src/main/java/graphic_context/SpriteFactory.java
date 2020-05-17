package graphic_context;

import java.awt.*;

public class SpriteFactory {
    public static SpriteSheet createSprite(Image image) {
        return new SpriteSheet( image ) {
        };
    }
}
