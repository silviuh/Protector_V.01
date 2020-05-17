package graphic_context;

import Constants.Constants;

public class TileFactory {
    public static Tile createTile(SpriteSheet spriteSheet, Constants.tileProperty property) {
        return new Tile( spriteSheet, property );
    }
}
