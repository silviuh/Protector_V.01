package factories;

import Constants.Constants;
import ecs_container.towers.ArcaneTower;
import utilities.Clock;

/**
 * Factory class used for creating timing clocks for different types of entities.
 */
public class ClockFactory {

    private ClockFactory() {


    }

    public static Clock createInstance(Constants.clockType clockType) {
        Clock clock = null;

        switch (clockType) {
            case ENEMY_1: {
                clock = new Clock(
                        1,
                        Constants.ENEMY_1_UPDATE_FRAME_DURATION
                );
                break;
            }
            case ENEMY_2: {
                clock = new Clock(
                        1,
                        Constants.ENEMY_2_UPDATE_FRAME_DURATION
                );
                break;
            }
            case ENEMY_3: {
                clock = new Clock(
                        1,
                        Constants.ENEMY_3_UPDATE_FRAME_DURATION
                );
                break;
            }
            case ENEMY_4: {
                clock = new Clock(
                        1,
                        Constants.ENEMY_4_UPDATE_FRAME_DURATION
                );
                break;
            }
            case ENEMY_5: {
                clock = new Clock(
                        1,
                        Constants.ENEMY_5_UPDATE_FRAME_DURATION
                );
                break;
            }
            case ENEMY_6: {
                clock = new Clock(
                        1,
                        Constants.ENEMY_6_UPDATE_FRAME_DURATION
                );
                break;
            }
            case ENEMY_7: {
                clock = new Clock(
                        1,
                        Constants.ENEMY_7_UPDATE_FRAME_DURATION
                );
                break;
            }
            case ENEMY_8: {
                clock = new Clock(
                        1,
                        Constants.ENEMY_8_UPDATE_FRAME_DURATION
                );
                break;
            }

            case WAVE_1: {
                clock = new Clock(
                        1,
                        Constants.WAVE_1_UPDATE_FRAME_DURATION
                );
                break;
            }

            case WAVE_2: {
                clock = new Clock(
                        1,
                        Constants.WAVE_2_UPDATE_FRAME_DURATION
                );
                break;
            }

            case WAVE_3: {
                clock = new Clock(
                        1,
                        Constants.WAVE_3_UPDATE_FRAME_DURATION
                );
                break;
            }

            case CRANE: {
                clock = new Clock(
                        1,
                        Constants.CRANE_UPDATE_FRAME_DURATION
                );
                break;
            }

            case CANNON: {
                clock = new Clock(
                        1,
                        Constants.CANNON_UPDATE_FRAME_DURATION
                );
                break;
            }

            case ZOMBIE: {
                clock = new Clock(
                        1,
                        Constants.ZOMBIE_UPDATE_FRAME_DURATION
                );
                break;
            }

            case ARCANE: {
                clock = new Clock(
                        1,
                        Constants.ARCANE_UPDATE_FRAME_DURATION
                );
                break;
            }

            case PROJECTILE_TYPE_ZOMBIE: {
                clock = new Clock(
                        1,
                        Constants.PROJECTILE_ZOMBIE_UPDATE_FRAME_DURATION
                );
                break;
            }
            case PROJECTILE_TYPE_ARCANE: {
                clock = new Clock(
                        1,
                        Constants.PROJECTILE_ARCANE_UPDATE_FRAME_DURATION
                );
                break;
            }
            case PROJECTILE_TYPE_CRANE: {
                clock = new Clock(
                        1,
                        Constants.PROJECTILE_CRANE_UPDATE_FRAME_DURATION
                );
                break;
            }
            case PROJECTILE_TYPE_CANNON: {
                clock = new Clock(
                        1,
                        Constants.PROJECTILE_CANNON_UPDATE_FRAME_DURATION
                );
                break;
            }

            case UI_CONSOLE: {
                clock = new Clock(
                        1,
                        Constants.CONSOLE_UPDATE_FRAME_DURATION
                );
                break;
            }
        }
        return clock;
    }
}