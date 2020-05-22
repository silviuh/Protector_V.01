package Constants;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static class PairOfCoordinates {
        private int xCoord;
        private int yCoord;

        public PairOfCoordinates(int xCoord, int yCoord) {
            this.xCoord = xCoord;
            this.yCoord = yCoord;
        }

        public int getxCoord() {
            return xCoord;
        }

        public int getyCoord() {
            return yCoord;
        }
    }

    public static ArrayList< PairOfCoordinates > directions;

    static {
        directions = new ArrayList< PairOfCoordinates >();
        directions.add(
                new PairOfCoordinates( 0, -1 )
        );
        directions.add(
                new PairOfCoordinates( 1, 0 )
        );
        directions.add(
                new PairOfCoordinates( 0, 1 )
        );
        directions.add(
                new PairOfCoordinates( -1, 0 )
        );
    }

    private Constants() {
    }

    public enum StateID {
        IDLE, INITIALIZED, PLAYING, PAUSED, GAME_OVER, DESTROYED
    }

    public enum tileProperty {
        UNINITIALIZED, BASIC, SOLID, TRAVERSABLE, PLACEABLE
    }

    public enum Image {
        BRUSH( "BRUSH", tileProperty.BASIC ),
        WATER_1( "WATER_1", tileProperty.BASIC ),
        WATER_2( "WATER_2", tileProperty.BASIC ),
        SAND( "SAND", tileProperty.TRAVERSABLE ),
        GRASS_1( "GRASS_1", tileProperty.TRAVERSABLE ),
        SOLID_WALL( "SOLID_WALL", tileProperty.SOLID ),
        MUD( "MUD", tileProperty.TRAVERSABLE ),
        GRASS_3( "GRASS_3", tileProperty.PLACEABLE ),
        ENEMY_KEEP( "ENEMY_KEEP", tileProperty.TRAVERSABLE ),
        ALLY_KEEP( "ALLY_KEEP", tileProperty.TRAVERSABLE ),

        CANNON_TOWER( "CANNON_TOWER", tileProperty.SOLID ),
        STONE_TOWER( "STONE_TOWER", tileProperty.SOLID ),
        ARCANE_TOWER( "ARCANE_TOWER", tileProperty.SOLID ),
        LIGHTNING_TOWER( "LIGHTNING_TOWER", tileProperty.SOLID ),
        GRASS_TOWER( "GRASS_TOWER", tileProperty.SOLID ),
        CRANE_TOWER( "CRANE_TOWER", tileProperty.SOLID ),
        ZOMBIE_TOWER( "ZOMBIE_TOWER", tileProperty.SOLID ),

        WOOD_BG( "WOOD_BG", tileProperty.BASIC ),
        USUAL_MOUSE_ICON( "USUAL_MOUSE_ICON", tileProperty.BASIC ),

        EXIT_BUTTON_1( "EXIT_BUTTON_1", tileProperty.BASIC ),
        PAUSE_BUTTON_1( "PAUSE_BUTTON_1", tileProperty.BASIC ),
        PLAY_BUTTON_1( "PLAY_BUTTON_1", tileProperty.BASIC ),
        HELP_BUTTON_1( "HELP_BUTTON_1", tileProperty.BASIC ),
        INCREASE_GAME_SPEED_1( "INCREASE_GAME_SPEED_1", tileProperty.BASIC ),
        DECREASE_GAME_SPEED_1( "DECREASE_GAME_SPEED_1", tileProperty.BASIC ),


        ENEMY_1( "", tileProperty.BASIC ),
        ENEMY_2( "", tileProperty.BASIC ),
        ENEMY_3( "", tileProperty.BASIC ),
        ENEMY_4( "", tileProperty.BASIC );


        private static final Map< tileProperty, Image > BY_PROPERTY  = new HashMap<>();
        private static final Map< String, Image >       BY_LABEL     = new HashMap<>();
        private static final Map< String, Image >       BY_TOWERNESS = new HashMap<>();

        static {
            for (Image e : values()) {
                BY_LABEL.put( e.label, e );
                BY_PROPERTY.put( e.property, e );
                BY_PROPERTY.put( e.property, e );
            }
        }

        public final String       label;
        public final tileProperty property;

        Image(String label, tileProperty property) {
            this.label = label;
            this.property = property;
        }

        public static Image getImageByLabel(String label) {
            return BY_LABEL.get( label );
        }

        public static Image getImageBySolidity(boolean solidity) {
            return BY_PROPERTY.get( solidity );
        }

    }


    public enum towerType {
        IN_GAME_ARCANE_TOWER,
        IN_GAME_CANNON_TOWER,
        IN_GAME_ZOMBIE_TOWER,
        IN_GAME_CRANE_TOWER
    }


    public enum attackType {
        UNDEFINED,
        SPLASH,
        FREEZE
        // to be continued
    }

    public enum attackBehaviour {
        ARCANE,
        CRANE,
        ZOMBIE,
        CANNON
    }

    public enum projectileType {
        CANNON_BLAST,
        ZOMBIE_MISSLE,
        ARCANE_BLAST,
        CRANE_TRACKER,
    }

    public enum enemyType {
        TYPE_1,
        TYPE_2,
        TYPE_3,
        TYPE_4,
        TYPE_5,
        UNDEFINED_TYPE
        // TYPE_6,
        // TYPE_7
    }

    public enum clockType {
        ENEMY_1,
        ENEMY_2,
        ENEMY_3,
        ENEMY_4,
        ENEMY_5,
        ENEMY_6,
        ENEMY_7,
        ENEMY_8,
        WAVE_1,
        CANNON,
        ZOMBIE,
        CRANE,
        ARCANE,
        PROJECTILE_TYPE_ZOMBIE,
        PROJECTILE_TYPE_ARCANE,
        PROJECTILE_TYPE_CRANE,
        PROJECTILE_TYPE_CANNON,
        UI_CONSOLE
    }

    public static final String TITLE = "Tower Defence v.02";

    public static final int MAP_SIZE                = 25;
    public static final int TILE_SIZE               = 42;
    public static final int ENEMY_HEALTH_BAR_WIDTH  = 16;
    public static final int ENEMY_HEALTH_BAR_HEIGHT = 8;

    public static final int    PROJECTILE_SIZE    = 21;
    public static final double PROJECTILE_PADDING = 10.5f;
    public static final double PROJECTILE_SPEED   = 40.5f;

    public static final float ONE_SECOND = ( float ) 1000000000.0;


    public static final int Y_AXIS_TOWER_PADDING       = 265;
    public static final int X_UI_TOWER_COORDINATE      = 1060;
    public static final int TOWER_HEIGHT               = 235;
    public static final int TOWER_WIDTH                = 235;
    public static final int MOUSE_ICON_WIDTH           = 42;
    public static final int MOUSE_ICON_HEIGHT          = 42;
    public static final int MAIN_MENU_BUTTON_HEIGHT    = 50;
    public static final int MAIN_MENU_BUTTON_WIDTH     = 300;
    public static final int TITLE_CENTER_WIDTH_PADDING = 100;
    public static final int MAIN_MENU_BUTTON_PADDING   = 75;
    public static final int MENU_CENTER_Y_SPACING      = 200;
    public static final int TITLE_PADDING_FROM_BUTTONS = 12;
    public static final int TEXT_SIZE                  = 600;
    public static final int TEXT_X_PADDING             = 250;
    public static final int TEXT_Y_PADDING             = 200;
    public static final int BOARD_WIDTH                = 1050;
    public static final int BOARD_HEIGHT               = 1050;
    public static final int UI_WOOD_WIDTH              = 255;
    public static final int UI_WOOD_HEIGHT             = 265;
    public static final int RADIUS_CIRCLE_COLOR_1      = 107;

    public static final int HEART_BAR_WIDTH                 = 300;
    public static final int HEART_BAR_HEIGHT                = 50;
    public static final int HEART_BAR_X                     = 1500;
    public static final int HEART_FOREGROUND_PADDING        = 58;
    public static final int HEART_FOREGROUND_HEIGHT_PADDING = 11;
    public static final int HEART_BAR_Y                     = 25;

    public static final int DOLLAR_SIGN_WIDTH     = 100;
    public static final int DOLLAR_SIGN_HEIGHT    = 180;
    public static final int DOLLAR_SIGN_X         = 1350;
    public static final int DOLLAR_SIGN_Y         = 0;
    public static final int DOLLAR_SIGN_X_PADDING = 115;
    public static final int DOLLAR_SIGN_Y_PADDING = 135;

    public static final int FPS_RATE_X_PADDING = 10;
    public static final int FPS_RATE_Y_PADDING = 0;
    public static final int FPS_RATE_X         = DOLLAR_SIGN_X + FPS_RATE_X_PADDING;
    public static final int FPS_RATE_Y         = DOLLAR_SIGN_Y + 250;

    public static final int IN_GAME_MENU_X      = 1075;
    public static final int IN_GAME_MENU_Y      = 0;
    public static final int IN_GAME_MENU_WIDTH  = 1500;
    public static final int IN_GAME_MENU_HEIGHT = 1500;

    public static final int IN_GAME_UPGRADE_BUTTON_WIDTH  = 500;
    public static final int IN_GAME_UPGRADE_BUTTON_HEIGHT = 70;
    public static final int IN_GAME_SELL_BUTTON_WIDTH     = 500;
    public static final int IN_GAME_SELL_BUTTON_HEIGHT    = 70;

    public static final int IN_GAME_UPGRADE_BUTTON_X = DOLLAR_SIGN_X + 10;
    public static final int IN_GAME_UPGRADE_BUTTON_Y = FPS_RATE_Y + 50;
    public static final int IN_GAME_SELL_BUTTON_X    = DOLLAR_SIGN_X + 10;
    public static final int IN_GAME_SELL_BUTTON_Y    = FPS_RATE_Y + 135;

    public static final int SELL_TEXT_X_PADDING    = 130;
    public static final int SELL_TEXT_Y_PADDING    = 48;
    public static final int UPGRADE_TEXT_X_PADDING = 80;
    public static final int UPGRADE_TEXT_Y_PADDING = 48;

    public static final int CONSOLE_WIDTH                = 700;
    public static final int CONSOLE_HEIGHT               = 800;
    public static final int CONSOLE_X                    = IN_GAME_UPGRADE_BUTTON_X - 50;
    public static final int CONSOLE_Y                    = IN_GAME_UPGRADE_BUTTON_Y + 202;
    public static final int CONSOLE_FIRST_MESSAGE_BORDER = 15;

    public static final int BUY_HISTORY_X                 = IN_GAME_UPGRADE_BUTTON_X - 50;
    public static final int BUY_HISTORY_Y                 = IN_GAME_UPGRADE_BUTTON_Y + 202 + 495;
    public static final int BUY_HISTORY_IMAGE_WIDTH       = 60;
    public static final int BUY_HISTORY_IMAGE_HEIGHT      = 60;
    public static final int BUY_HISTORY_X_PADDING         = 70;
    public static final int BUY_HISTORY_MESSAGE_Y_PADDING = 15;

    public static final int NR_OF_DIRECTIONS = 4;

    public static final int     MAX_LIVES_AMOUNT_LEVEL_1      = 40;
    public static final Integer GAME_SPEED                    = 50;
    public static final int     ENEMY_1_UPDATE_FRAME_DURATION = 600;
    public static final int     ENEMY_2_UPDATE_FRAME_DURATION = 500;
    public static final int     ENEMY_3_UPDATE_FRAME_DURATION = 200;
    public static final int     ENEMY_4_UPDATE_FRAME_DURATION = 300;
    public static final int     ENEMY_5_UPDATE_FRAME_DURATION = 700;

    public static final int ENEMY_6_UPDATE_FRAME_DURATION = 600;
    public static final int ENEMY_7_UPDATE_FRAME_DURATION = 700;
    public static final int ENEMY_8_UPDATE_FRAME_DURATION = 800;

    public static final int CANNON_UPDATE_FRAME_DURATION = 200;
    public static final int CRANE_UPDATE_FRAME_DURATION  = 200;
    public static final int ARCANE_UPDATE_FRAME_DURATION = 200;
    public static final int ZOMBIE_UPDATE_FRAME_DURATION = 200;

    public static final int PROJECTILE_ZOMBIE_UPDATE_FRAME_DURATION = 80;
    public static final int PROJECTILE_ARCANE_UPDATE_FRAME_DURATION = 300;
    public static final int PROJECTILE_CRANE_UPDATE_FRAME_DURATION  = 80;
    public static final int PROJECTILE_CANNON_UPDATE_FRAME_DURATION = 80;

    public static final int WAVE_1_UPDATE_FRAME_DURATION  = 1000;
    public static final int CONSOLE_UPDATE_FRAME_DURATION = 1000;


    public static final int CRANE_TOWER_COST    = 50;
    public static final int CANNON_TOWER_COST   = 80;
    public static final int ZOMBIE_TOWER_COST   = 100;
    public static final int ARCANE_TOWER_COST   = 60;
    public static final int CANNON_UPGRADE_COST = 20;
    public static final int ZOMBIE_UPGRADE_COST = 40;
    public static final int CRANE_UPGRADE_COST  = 15;
    public static final int ARCANE_UPGRADE_COST = 20;

    public static final int NR_OF_AVAILABLE_GAME_SAVINGS = 5;

    public static final int DEVIL_MONEY_INCREASE_ON_DEATH = 15;
    public static final int OWL_MONEY_INCREASE_ON_DEATH   = 25;
    public static final int SONIC_MONEY_INCREASE_ON_DEATH = 60;
    public static final int SLIME_MONEY_INCREASE_ON_DEATH = 10;
    public static final int GROOT_MONEY_INCREASE_ON_DEATH = 20;

    public static final Color UIConsole_bg_color = new Color( 255, 176, 231, 55 );
    public static final Color dollarSign         = new Color( 255, 230, 140 );

    public static final Color cannonOuter = Color.white;
    public static final Color cannonInner = new Color( 127, 127, 127, 127 );
    public static final Color cannonText  = Color.white;

    public static final Color arcaneInner = new Color( 142, 142, 69, 65 );
    public static final Color arcaneOuter = new Color( 255, 255, 102 );
    public static final Color arcaneText  = new Color( 255, 255, 102 );

    public static final Color craneInner = new Color( 51, 0, 102, 65 );
    public static final Color craneOuter = new Color( 0, 255, 255 );
    public static final Color craneText  = new Color( 0, 255, 255 );

    public static final Color zombieInner = new Color( 0, 204, 204, 65 );
    public static final Color zombieOuter = new Color( 22, 22, 23 );
    public static final Color zombieText  = new Color( 22, 22, 23 );

    public static int CANNON_TOWER_RANGE = 200;
    public static int ZOMBIE_TOWER_RANGE = 140;
    public static int CRANE_TOWER_RANGE  = 140;
    public static int ARCANE_TOWER_RANGE = 140;
    public static int RANGE_REFINE       = 7;

    public static final int ARCANE_TOWER_DAMAGE_INCREASE_PER_LEVEL      = 10;
    public static final int ARCANE_TOWER_DECREASE_IN_COOLDOWN_PER_LEVEL = 20;
    public static final int ARCANE_TOWER_INCREASE_IN_RANGE_PER_LEVEL    = 15;

    public static final int CRANE_TOWER_DAMAGE_INCREASE_PER_LEVEL      = 20;
    public static final int CRANE_TOWER_DECREASE_IN_COOLDOWN_PER_LEVEL = 20;
    public static final int CRANE_TOWER_INCREASE_IN_RANGE_PER_LEVEL    = 15;

    public static final int CANNON_TOWER_DAMAGE_INCREASE_PER_LEVEL      = 40;
    public static final int CANNON_TOWER_DECREASE_IN_COOLDOWN_PER_LEVEL = 20;
    public static final int CANNON_TOWER_INCREASE_IN_RANGE_PER_LEVEL    = 25;

    public static final int ZOMBIE_TOWER_DAMAGE_INCREASE_PER_LEVEL      = 5;
    public static final int ZOMBIE_TOWER_DECREASE_IN_COOLDOWN_PER_LEVEL = 20;
    public static final int ZOMBIE_TOWER_INCREASE_IN_RANGE_PER_LEVEL    = 20;

    public static final int ARCANE_TOWER_UPGRADE_COST = 20;
    public static final int ARCANE_REFUND_VALUE       = 30;
    public static final int ARCANE_MAX_LEVEL          = 3;

    public static final int CRANE_TOWER_UPGRADE_COST = 20;
    public static final int CRANE_REFUND_VALUE       = 30;
    public static final int CRANE_MAX_LEVEL          = 3;

    public static final int CANNON_TOWER_UPGRADE_COST = 20;
    public static final int CANNON_REFUND_VALUE       = 30;
    public static final int CANNON_MAX_LEVEL          = 3;

    public static final int ZOMBIE_TOWER_UPGRADE_COST = 40;
    public static final int ZOMBIE_REFUND_VALUE       = 40;
    public static final int ZOMBIE_MAX_LEVEL          = 3;

    public static int CANNON_TOWER_BASE_DAMAGE = 50;
    public static int ZOMBIE_TOWER_BASE_DAMAGE = 15;
    public static int CRANE_TOWER_BASE_DAMAGE  = 35;
    public static int ARCANE_TOWER_BASE_DAMAGE = 35;

    public static int CANNON_TOWER_ENEMIES_AFFECTED = 1;
    public static int ZOMBIE_TOWER_ENEMIES_AFFECTED = 1;
    public static int CRANE_TOWER_ENEMIES_AFFECTED  = 1;
    public static int ARCANE_TOWER_ENEMIES_AFFECTED = 1;

    public static int LIVES_TAKEN_BY_GROOT = -1;
    public static int LIVES_TAKEN_BY_OWL   = -2;
    public static int LIVES_TAKEN_BY_DEVIL = -1;
    public static int LIVES_TAKEN_BY_SONIC = -3;
    public static int LIVES_TAKEN_BY_SLIME = -1;

    public static int UI_CONSOLE_CAPACITY        = 11;
    public static int BUY_HISTORY_CAPACITY       = 10;
    public static int CONSOLE_MESSAGES_X_PADDING = 3;
    public static int CONSOLE_MESSAGES_Y_PADDING = 50;

    public static String ARCANE_NAME = new String( "ARCANE" );
    public static String CRANE_NAME  = new String( "CRANE" );
    public static String CANNON_NAME = new String( "CANNON" );
    public static String ZOMBIE_NAME = new String( "ZOMBIE" );


    public static final int  FPS                    = 60;
    public static final long TIME_FRAME             = 1_000_000_000 / FPS;
    public static final int  ALLY_KEEP_TILE_CODE    = 9;
    public static final int  ENEMY_KEEP_TILE_CODE   = 8;
    public static final int  NR_OF_TILES_PER_WIDTH  = 25;
    public static final int  NR_OF_TILES_PER_HEIGHT = 25;

    public static final String UPGRADE_TEXT = "UPGRADE TOWER";
    public static final String SELL_TEXT    = "SELL TOWER";

    public static final String KENVECTOR_FUTURE_THIN_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\fonts\\kenvector_future_thin.ttf";
    public static final String KENVECTOR_FUTURE_URL      = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\fonts\\kenvector_future.ttf";
    public static final String UBUNTU_FONT_BOLD          = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\fonts\\Ubuntu-R.ttf";

    public static final String USUAL_MOUSE_ICON_URL       = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\mouse_icons\\mouse_4.png";
    public static final String IN_GAME_UPGRADE_BUTTON_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\in_game_buttons\\2.png";
    public static final String IN_GAME_SELL_BUTTON        = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\in_game_buttons\\2.png";
    public static final String UI_CONSOLE_BG              = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\in_game_console\\consle_bg.jpg";

    public static final String UPGRADE_HAMMER_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\UI_assets\\hammer_2.png";
    public static final String SELL_BUTTON_URL    = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\in_game_buttons\\2.png";
    public static final String SELL_ICON_URL      = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\in_game_icons\\sell_dollar_3.png";

    public static final String SAVE_GAME_ICON_URL = "C:\\Users\\silviu\\IdeaProjects\\Protector_Maven\\src\\main\\java\\assets\\in_game_icons\\save_3.png";

    public static final String IN_GAME_MENU_BACKGROUND = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\in_game_menu_bg\\5.jpg";

    public static final String HEARTH_BAR_FOREGROUND_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\UI_assets\\heart_foreground.png";
    public static final String HEARTH_BAR_BACKGROUND_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\UI_assets\\heart_background.png";
    public static final String DOLLAR_SIGN_URL           = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\UI_assets\\dollar.png";

    public static final String GAME_ICON            = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\extra\\game_icon.png";
    public static final String BACKGROUND_IMAGE_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\bg_images\\bg_1.jpg";
    public static final String BACKGROUND_GIF_URL   = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\bg_gifs\\10.gif";
    public static final String HELP_BACKGROUND_GIF  = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\bg_gifs\\12.gif";

    public static final String HEALTH_BAR_FOREGROUND_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\health_bars\\ZmsIMFg.png";
    public static final String HEALTH_BAR_BACKGROUND_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\health_bars\\3kNOcPH.png";
    public static final String HEALTH_BAR_BORDER_URL     = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\health_bars\\BLtsNCJ.png";

    public static final String SOLID_WALL_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\map_images\\graybrick.png";
    public static final String BRUSH_URL      = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\map_images\\bush.png";
    public static final String WATER_1        = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\map_images\\water_1.png";
    public static final String WATER_2        = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\map_images\\water_2.png";
    public static final String GRASS_1        = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\map_images\\grass_1.png";
    public static final String MUD            = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\map_images\\mud.png";
    public static final String GRASS_3        = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\map_images\\grass_3.png";
    public static final String SAND           = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\map_images\\sand.png";
    public static final String ENEMY_KEEP     = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\castles\\3.png";
    public static final String ALLY_KEEP      = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\castles\\2.png";

    public static final String PROJECTILE_TYPE_ZOMBIE_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\projectiles\\4.png";
    public static final String PROJECTILE_TYPE_CRANE_URL  = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\projectiles\\15.png";
    public static final String PROJECTILE_TYPE_ARCANE_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\projectiles\\0.png";
    public static final String PROJECTILE_TYPE_CANNON_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\projectiles\\12.png";

    public static final String BLOOD_SPILL_1_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\blood_spill\\blood_spill_1.png";
    public static final String BLOOD_SPILL_2_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\blood_spill\\blood_spill_2.png";
    public static final String BLOOD_SPILL_3_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\blood_spill\\blood_spill_3.png";
    public static final String BLOOD_SPILL_4_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\blood_spill\\blood_spill_4.png";

    public static final String CANNON_TOWER_URL    = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\towers\\tower_grass.png";
    //public static final String CANNON_TOWER_URL    = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\cannon_tower.gif";
    public static final String STONE_TOWER_URL     = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\stone_tower.png";
    // public static final String ARCANE_TOWER_URL    = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\arcane_tower.gif";
    public static final String ARCANE_TOWER_URL    = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\towers\\arcane_v4.png";
    public static final String LIGHTNING_TOWER_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\tower_lightning.png";
    public static final String GRASS_TOWER_URL     = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\tower_grass.png";
    // public static final String CRANE_TOWER_URL     = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\crane_tower.gif";
    public static final String CRANE_TOWER_URL     = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\towers\\crane_v2.png";
    public static final String ZOMBIE_TOWER_URL    = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\zombie_tower.gif";

    public static final String EXIT_BUTTON_1_URL         = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\menu_icons\\resized_icons\\exit_2.png";
    public static final String GAME_SPEED_BUTTON         = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\menu_icons\\resized_icons\\game_speed.png";
    public static final String HELP_BUTTON_1_URL         = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\menu_icons\\resized_icons\\help.png";
    public static final String PAUSE_BUTTON_1_URL        = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\mouse_icons\\mouse_7.png";
    public static final String PLAY_BUTTON_1_URL         = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\menu_icons\\resized_icons\\play.png";
    public static final String INCREASE_GAME_SPEED_1_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\in_game_icons\\grass_1.png";
    public static final String DECREASE_GAME_SPEED_1_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\in_game_icons\\bush.png";
    public static final String QUESTION_MARK_URL         = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\menu_icons\\resized_icons\\question_mark.png";
    public static final String MENU_BUTTON_ICON_URL      = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\menu_icons\\resized_icons\\resized_menu_btn_2.png";


    public static final String ENEMY_1_IMG_1_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_1\\1.png";
    public static final String ENEMY_1_IMG_2_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_1\\2.png";
    public static final String ENEMY_1_IMG_3_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_1\\3.png";
    public static final String ENEMY_1_IMG_4_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_1\\4.png";

    public static final String ENEMY_2_IMG_1_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_2\\1.png";
    public static final String ENEMY_2_IMG_2_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_2\\2.png";
    public static final String ENEMY_2_IMG_3_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_2\\3.png";
    public static final String ENEMY_2_IMG_4_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_2\\4.png";

    public static final String ENEMY_3_IMG_1_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_3\\1.png";
    public static final String ENEMY_3_IMG_2_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_3\\2.png";
    public static final String ENEMY_3_IMG_3_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_3\\3.png";
    public static final String ENEMY_3_IMG_4_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_3\\4.png";
    public static final String ENEMY_3_IMG_5_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_3\\5.png";
    public static final String ENEMY_3_IMG_6_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_3\\6.png";

    public static final String ENEMY_4_IMG_1_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_4\\1.png";
    public static final String ENEMY_4_IMG_2_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_4\\2.png";
    public static final String ENEMY_4_IMG_3_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_4\\3.png";
    public static final String ENEMY_4_IMG_4_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_4\\4.png";
    public static final String ENEMY_4_IMG_5_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\enemy_4\\5.png";

    public static final String ENEMY_5_IMG_1_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\slime\\1.png";
    public static final String ENEMY_5_IMG_2_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\slime\\2.png";
    public static final String ENEMY_5_IMG_3_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\slime\\3.png";
    public static final String ENEMY_5_IMG_4_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\slime\\4.png";

    public static final String ENEMY_8_IMG_1_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\bubble\\1.png";
    public static final String ENEMY_8_IMG_2_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\bubble\\2.png";
    public static final String ENEMY_8_IMG_3_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\bubble\\3.png";

    public static final String ENEMY_6_IMG_1_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_1\\1.png";
    public static final String ENEMY_6_IMG_2_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_1\\2.png";
    public static final String ENEMY_6_IMG_3_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_1\\3.png";
    public static final String ENEMY_6_IMG_4_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_1\\4.png";
    public static final String ENEMY_6_IMG_5_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_1\\5.png";
    public static final String ENEMY_6_IMG_6_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_1\\6.png";


    public static final String ENEMY_7_IMG_1_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_2\\1.png";
    public static final String ENEMY_7_IMG_2_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_2\\2.png";
    public static final String ENEMY_7_IMG_3_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_2\\3.png";
    public static final String ENEMY_7_IMG_4_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_2\\4.png";
    public static final String ENEMY_7_IMG_5_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_2\\5.png";
    public static final String ENEMY_7_IMG_6_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_2\\6.png";
    public static final String ENEMY_7_IMG_7_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\enemies_sprites\\orc_2\\7.png";


    public static final String LEVEL_1_MAP_CONFIG_TXT = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\config_files\\level_1.txt";
    public static final String HELP_TEXT_CONFIG_TXT   = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\config_files\\help_menu.txt";

    public static final String WOOD_BG_URL = "C:\\Users\\silviu\\IdeaProjects\\Tower_Defence_v02\\src\\assets\\wood_bg_4.jpg";

    public static final int NR_OF_UI_TOWERS = 4;


    public static final String PLAY_GAME_BTN_LABEL     = "START GAME";
    public static final String HELP_BTN_LABEL          = "HELP";
    public static final String BACK_TO_MAIN_MENU_LABEL = "BACK TO MENU";
    public static final String SETTINGS_BTN_LABEL      = "SETTINGS";
    public static final String HIGH_SCORE_BTN_LABEL    = "HIGH SCORE";
    public static final String EXIT_GAME_LABEL         = "EXIT";


    public static class HandHolder {
        public int isInHand = 0;
        public int handXpos = 0;
        public int handYpos = 0;
    }
}
