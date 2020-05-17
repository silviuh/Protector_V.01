package factories;

import Constants.Constants;
import graphic_context.SpriteSheet;

import javax.swing.*;
import java.util.ArrayList;

public class SpriteContainerFactory {
    private SpriteContainerFactory() {

    }

    public static ArrayList< SpriteSheet > createSprite(Constants.enemyType enemyType) {
        ArrayList< SpriteSheet > sprites = new ArrayList< SpriteSheet >();

        switch (enemyType) {
            case TYPE_1: {
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_1_IMG_1_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_1_IMG_2_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_1_IMG_3_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_1_IMG_4_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                break;
            }
            case TYPE_2: {
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_2_IMG_1_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_2_IMG_2_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_2_IMG_3_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_2_IMG_4_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                break;
            }
            case TYPE_3: {
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_3_IMG_1_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_3_IMG_2_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_3_IMG_3_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_3_IMG_4_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_3_IMG_5_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_3_IMG_6_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                break;
            }
            case TYPE_4: {
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_4_IMG_1_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_4_IMG_2_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_4_IMG_3_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_4_IMG_4_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_4_IMG_5_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                break;
            }

            case TYPE_5: {
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_5_IMG_1_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_5_IMG_2_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_5_IMG_3_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
                sprites.add(
                        new SpriteSheet(
                                Constants.tileProperty.BASIC,
                                new ImageIcon( Constants.ENEMY_5_IMG_4_URL ).getImage(),
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                        )
                );
            }
        }
        return sprites;
    }
}
