package factories;

import Constants.Constants;

import javax.swing.*;

public class ImageFactory {
    public static ImageIcon createImage(Constants.Image image) {

        ImageIcon imageIcon = null;

        switch (image) {
            case SOLID_WALL: {
                imageIcon = new ImageIcon( Constants.SOLID_WALL_URL );
                break;
            }
            case BRUSH: {
                imageIcon = new ImageIcon( Constants.BRUSH_URL );
                break;
            }
            case WATER_1: {
                imageIcon = new ImageIcon( Constants.WATER_1 );
                break;
            }
            case WATER_2: {
                imageIcon = new ImageIcon( Constants.WATER_2 );
                break;
            }
            case GRASS_1: {
                imageIcon = new ImageIcon( Constants.GRASS_1 );
                break;
            }
            case MUD: {
                imageIcon = new ImageIcon( Constants.MUD );
                break;
            }
            case GRASS_3: {
                imageIcon = new ImageIcon( Constants.GRASS_3 );
                break;
            }
            case SAND: {
                imageIcon = new ImageIcon( Constants.SAND );
                break;
            }
            case ENEMY_KEEP: {
                imageIcon = new ImageIcon( Constants.ENEMY_KEEP );
                break;
            }
            case ALLY_KEEP: {
                imageIcon = new ImageIcon( Constants.ALLY_KEEP );
                break;
            }

            case ARCANE_TOWER: {
                imageIcon = new ImageIcon( Constants.ARCANE_TOWER_URL );
                break;
            }
            case CANNON_TOWER: {
                imageIcon = new ImageIcon( Constants.CANNON_TOWER_URL );
                break;
            }
            case GRASS_TOWER: {
                imageIcon = new ImageIcon( Constants.GRASS_TOWER_URL );
                break;
            }
            case LIGHTNING_TOWER: {
                imageIcon = new ImageIcon( Constants.LIGHTNING_TOWER_URL );
                break;
            }
            case CRANE_TOWER: {
                imageIcon = new ImageIcon( Constants.CRANE_TOWER_URL );
                break;
            }
            case ZOMBIE_TOWER: {
                imageIcon = new ImageIcon( Constants.ZOMBIE_TOWER_URL );
                break;
            }
            case WOOD_BG: {
                imageIcon = new ImageIcon( Constants.WOOD_BG_URL );
                break;
            }
            case USUAL_MOUSE_ICON: {
                imageIcon = new ImageIcon( Constants.USUAL_MOUSE_ICON_URL );
                break;
            }
            case PLAY_BUTTON_1: {
                imageIcon = new ImageIcon( Constants.PLAY_BUTTON_1_URL );
                break;
            }
            case HELP_BUTTON_1: {
                imageIcon = new ImageIcon( Constants.HELP_BUTTON_1_URL );
                break;
            }
            case EXIT_BUTTON_1: {
                imageIcon = new ImageIcon( Constants.EXIT_BUTTON_1_URL );
                break;
            }
            case PAUSE_BUTTON_1: {
                imageIcon = new ImageIcon( Constants.PAUSE_BUTTON_1_URL );
                break;
            }
            case ENEMY_1: {
                imageIcon = new ImageIcon( Constants.PAUSE_BUTTON_1_URL );
                break;
            }
            case ENEMY_2: {
                imageIcon = new ImageIcon( Constants.PAUSE_BUTTON_1_URL );
                break;
            }
            case ENEMY_3: {
                imageIcon = new ImageIcon( Constants.PAUSE_BUTTON_1_URL );
                break;
            }
            case ENEMY_4: {
                imageIcon = new ImageIcon( Constants.PAUSE_BUTTON_1_URL );
                break;
            }
        }
        return imageIcon;
    }
}
